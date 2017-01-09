import argparse
import uuid
import pymysql.cursors
from faker import Factory

# database connection parameters
connectConfig = {
    'user': 'guest',
    'password': 'guest',
    'host': 'ec2-35-160-73-118.us-west-2.compute.amazonaws.com',
    'port': 3306,
    'database': 'kiddodb'
}

# map of table names to table columns
schema = {
    'administrators': ['idSchool', 'uname', 'pass', 'email'],
    'teachers': ['idTeacher', 'idSchool', 'email', 'uname', 'pass', 'fName', 'lName'],
    'classes': ['idClass', 'idSchool', 'idTeacher', 'name'],
    'students': ['idStudent', 'name', 'idClass', 'idParent1', 'idParent2'],
    'parents': ['idParent', 'fName', 'lName', 'email', 'uname', 'pass', 'idStudent'],
    'events': ['idEvent', 'idClass', 'name', 'location', 'startTime', 'endTime', 'comments'],
    'messages': ['idMessage', 'idSender', 'idRecipient', 'creationTime', 'readTime', 'body', 'media'],
    'announcements': ['postTime','idClass','title','body','media']
}

# parameters that can be specified on the command-line for fake data
opts = {'idteacher', 'idSchool', 'email', 'uname', 'password', 'fName', 'lName', 'idClass', 
        'name','idStudent', 'idParent1', 'idParent2','idParent', 'location', 'startTime', 
        'endTime', 'comments'}

def stringify(string):
    return "'" + string + "'"

def fakeEntry(table):
    uid = uuid.uuid4()

    # get parameters set on command-line 
    fname = ns.fname if ns.fname else fake.first_name()
    lname = ns.lname if ns.lname else fake.last_name()
    email = ns.email if ns.email else fake.email()
    schoolid = ns.idschool if ns.idschool else str(uid.time_mid)
    parentid = ns.idparent if ns.idparent else str(uid.time_hi_version)
    teacherid = ns.idteacher if ns.idteacher else str(uid.time_hi_version)
    classid = ns.idclass if ns.idclass else str(uid.clock_seq)
    ### TO DO: password encryption
    passwd = (ns.password if ns.password else 
              fake.password(length=10, special_chars=True, digits=True, 
                            upper_case=True, lower_case=True))

    name = ns.name if ns.name else fake.text(max_nb_chars=40)

    location = (ns.location if ns.location else 
                ('[' + str(fake.latitude()) + ' ' + str(fake.longitude()) + ']'))

    time1 = fake.date_time_this_year(before_now=True, after_now=True, tzinfo=None)
    time2 = fake.date_time_this_year(before_now=True, after_now=True, tzinfo=None)
    start = (ns.starttime if ns.starttime else 
             (time1.isoformat(' ') if time1 < time2 else time2.isoformat(' ')))
    end = (ns.endtime if ns.endtime else 
             (time1.isoformat(' ') if time1 > time2 else time2.isoformat(' ')))
    

    # 'administrators': ['idSchool', 'uname', 'pass', 'email']    
    if table == 'administrators':

        uname = ns.uname if ns.uname else fake.word() + str(uid.clock_seq)

        entry = [
            schoolid,
            stringify(uname),
            stringify(passwd),
            stringify(email)
        ]
        return entry


    # 'teachers': ['idTeacher', 'idSchool', 'email', 'uname', 'pass', 'fName', 'lName']
    if table == 'teachers':

        uname = ns.uname if ns.uname else fname[:2] + lname[:3] + teacherid[-3:]

        entry = [
            teacherid,
            schoolid,
            stringify(email),
            stringify(uname),
            stringify(passwd),
            stringify(fname),
            stringify(lname)
        ]
        return entry
        
    # 'classes': ['idClass', 'idSchool', 'idTeacher', 'name']
    if table == 'classes':
        ### TO DO: create fake class template
        entry = [
#            ,
            schoolid,
 #           ,
            fake.text(max_nb_chars=40)
        ]
        return entry

    # 'students': ['idStudent', 'name', 'idClass', 'idParent1', 'idParent2']
    if table == 'students':
        ### TO DO: create fake student template
        entry = []
        return entry

    # 'parents': ['idParent', 'fName', 'lName', 'email', 'uname', 'pass', 'idStudent']    
    if table == 'parents':

        uname = ns.uname if ns.uname else fname[:1] + lname[:4] + parentid[-4:]

        entry = [
            parentid,
            fname,
            lname,
            email,
            uname,
            passwd
        ]
        return entry

    # 'events': ['idEvent', 'idClass', 'name', 'location', 'startTime', 'endTime', 'comments']
    if table == 'events':

        entry = [
            str(uuid.uuid4()),
            classid,
            stringify(name),
            stringify(location),
            start,
            end,
            stringify(fake.text(max_nb_chars=400))
        ]
        return entry

    # 'messages': ['idMessage', 'idSender', 'idRecipient', 'creationTime', 'readTime', 'body', 'media']
    if table == 'messages':
        # TO DO: create fake message template
        entry = []
        return entry

    # 'announcements': ['postTime','idClass','title','body','media']
    if table == 'announcements':
        # TO DO: create fake announcement template
        entry = []
        return entry

    return None


### parse command-line arguments

parser = argparse.ArgumentParser(usage='python fake.py { -g -o OUTFILE -n #ENTRIES | -p -i INFILE } -t TABLE [ --SPECIFICATION ... ]',
                                 epilog='example usage:\n\n$ python fake.py -g -o fakeT.csv -n 50 -t teachers\n' +
                                 'Generate 50 fake teachers and store in file fakeT.csv\n\n' +
                                 '$ python fake.py -g -o fakeT.csv -n 50 -t teachers --idclass 20000\n' +
                                 'Generate 50 fake teachers with class ID 20000 and store in file fakeT.csv\n\n' +
                                 '$ python fake.py -g -p -o fakeC.csv -n 50 -t classes\n' +
                                 'Generate 50 fake classes, store in file fakeT.csv, and use to populate database\n\n'
                                 '$ python fake.py -g -o fakeA.csv -i fakeA2.csv -n 50 -t administrators\n' +
                                 'Generate 50 fake administrators and store in file fakeA.csv; read administrators from file ' +
                                 'fakeA2.csv and use to populate database\n\n' +
                                 '$ python fake.py -p -i fakeE.csv -t events\n' +
                                 'Read events from file fakeE.csv and use to populate database\n\n',
                                 formatter_class=argparse.RawTextHelpFormatter)

parser.add_argument('-t', '--table', choices=list(schema.keys()), required=True)
parser.add_argument('-v', '--verbose', action='store_true')

gen = parser.add_argument_group('generate-data', 'generate fake data')
pop = parser.add_argument_group('populate-db', 'populate database with fake data')
spec = parser.add_argument_group('specify', '(optional) specify value to be used for field in fake data')

gen.add_argument('-g', '--generate-data', action='store_true')
gen.add_argument('-o', '--outfile', type=argparse.FileType('w'),
                 help='name of csv file in which to store entries; not required if database is also being populated')
gen.add_argument('-n', '--num-entries', type=int)

pop.add_argument('-p', '--populate-db', action='store_true')
pop.add_argument('-i', '--infile', type=argparse.FileType('r'), 
                 help='name of csv file from which to read entries; not required if data is also being generated')

for opt in opts:
    spec.add_argument('--' + opt.lower())

ns = parser.parse_args()

if (not (ns.generate_data or ns.populate_db) or # an action is not selected
    (not ns.table) or # table is not specified
    (ns.generate_data and not ns.populate_db and not ns.outfile) or # missing output file
    (ns.populate_db and not ns.generate_data and not ns.infile) or # missing input file
    (ns.generate_data and not ns.num_entries) # number of entries to generate is missing
):
    parser.print_help()
    exit()


### main

table = ','.join(schema[ns.table])

if ns.populate_db:
    # connect to database
    cnx = pymysql.connect(**connectConfig)
    cur = cnx.cursor()
    ### TO DO: sanitize queries


### generate fake data

if ns.generate_data:
    fake = Factory.create()
    nentry = 0

    while (nentry < ns.num_entries):            
        entry = fakeEntry(ns.table)

        # write data to outfile in CSV format
        if ns.outfile:
            ns.outfile.write(','.join(entry) + '\n')

        # push newly-created data to database
        if ns.populate_db and not ns.infile:
            if ns.verbose:
                print("INSERT INTO {} ({}) VALUES ({});".format(
                    ns.table.capitalize(), table.upper(), ','.join(entry)
                ))

            cur.execute("INSERT INTO {} ({}) VALUES ({});".format(
                ns.table.capitalize(), table.upper(), ','.join(entry)
            ))
            cnx.commit()

        nentry += 1


    if ns.outfile:
        ns.outfile.close()


### push data from CSV file to database

if ns.populate_db:
    if ns.infile:

        for line in ns.infile:
            if ns.verbose:
                print("INSERT INTO {} ({}) VALUES ({});".format(
                    ns.table.capitalize(), table.upper(), line[:-1]
                ))

            cur.execute("INSERT INTO {} ({}) VALUES ({});".format(
                ns.table.capitalize(), table.upper(), line[:-1]
            ))
            cnx.commit()
    
        ns.infile.close()

    cur.close()
    cnx.close()
