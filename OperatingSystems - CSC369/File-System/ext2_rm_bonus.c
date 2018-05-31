#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <sys/mman.h>
#include <string.h>
#include <errno.h>
#include "ext2.h"

unsigned char *disk;
int flag;
struct ext2_super_block *sb;
struct ext2_group_desc *gd;

int delete_entry(struct ext2_dir_entry_2 *dir){
	struct ext2_inode *curr_inode = (struct ext2_inode *)(disk + (EXT2_BLOCK_SIZE * gd->bg_inode_table) + (sb->s_inode_size * (dir->inode - 1)));
	
	curr_inode->i_links_count--;
	if (!curr_inode->i_links_count){
		//delete the file
		int block;
		for (block = 0; block < 11; block++){
			gd->bg_block_bitmap &= ((1 << curr_inode->i_block[block]) >> (128 - curr_inode->i_block[block]));
		}
		gd->bg_inode_bitmap &= ((0 << dir->inode) >> (32 - dir->inode));
	}
	//set dtime
	return 0;
}

int delete_dir(struct ext2_dir_entry_2 *dir){
	struct ext2_dir_entry_2 *curr_dir = (struct ext2_dir_entry_2 *) NULL;
	struct ext2_inode *in = (struct ext2_inode *)(disk + (EXT2_BLOCK_SIZE * gd->bg_inode_table) + (sb->s_inode_size * (dir->inode - 1)));
	int i;
	for (i = 0; i < BLOCK_N; i++) {
		if (i < 12 && *(in->i_block + i)) {
			int remainding_blk_size = EXT2_BLOCK_SIZE;
			int off_set = 0;
			while (remainding_blk_size > 0) {
				curr_dir = (struct ext2_dir_entry_2 *) (disk + *(in->i_block) * EXT2_BLOCK_SIZE + off_set);
				delete_entry(curr_dir);         
				off_set = off_set + curr_dir->rec_len;
				remainding_blk_size = remainding_blk_size - curr_dir->rec_len;
			}
		}
		// Handle single indirection
		else {
			int j;
			for (j = 0; j < EXT2_BLOCK_SIZE - 4; j = j + 4) {
				if (*(disk + EXT2_BLOCK_SIZE * *(in->i_block + i) + j)) {
					int block_num = *(disk + EXT2_BLOCK_SIZE * *(in->i_block + i) + j);
					int remainding_blk_size = EXT2_BLOCK_SIZE;
					int off_set = 0;
					while (remainding_blk_size > 0) {
						curr_dir = (struct ext2_dir_entry_2 *) (disk + block_num * EXT2_BLOCK_SIZE + off_set);
						delete_entry(curr_dir);  
						off_set = off_set + curr_dir->rec_len;
						remainding_blk_size = remainding_blk_size - curr_dir->rec_len;
					}          
				}
			}           
		}
	}
	return 0;
}

int main(int argc, char **argv){
	char *disk_name = argv[1];
	char *path_name = argv[2];
	int flag_r = 0;
	int opt;
	
	while ((opt = getopt(argc, argv, "r")) != -1) {
		switch (opt) {
		case 'r':
			flag_r = 1;
			break;
		default:
			fprintf(stderr, "ext2_rm disk_name path_name [-r]\n" );
			exit(1);
		}
	}

	int fd = open(disk_name, O_RDWR);
	disk = mmap(NULL, 128 * EXT2_BLOCK_SIZE, PROT_READ | PROT_WRITE, MAP_SHARED, fd, 0);
	if(disk == MAP_FAILED) {
		perror("mmap");
		exit(1);
	}
	close(fd);
	sb = (struct ext2_super_block *)(disk + EXT2_BLOCK_SIZE);
	gd = (struct ext2_group_desc *)(disk + 2 * 1024);
	
	struct ext2_dir_entry_2 *dir = path_traversal(path_name);
	if (!dir){
		return ENOENT;
	}
	if (dir->file_type == EXT2_FT_DIR && flag_r){
		delete_dir(dir);
	}
	else{
		delete_entry(dir);
	}
 
	return 0;
}

