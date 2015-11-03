import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import org.jsoup.*;
import org.jsoup.nodes.Document;

public class RadioToText {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String fileName = "Nicky's Shit Music";
		BufferedWriter writer = null;
		//final JFrame parent = new JFrame();
		JButton button = new JButton();

		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(fileName + ".txt"));
			ArrayList songs = new ArrayList();
			String line = "";
			while (line != null)
			{
				line = reader.readLine();
				songs.add(line);
			}
			reader.close();
			writer = new BufferedWriter(new FileWriter(fileName + ".txt", true));
		}
		catch (Exception e)
		{
		}
		while (true)
		{
			try {
		        //Document doc = Jsoup.connect("https://www.radiodanz.com/playlist/nowPlaying.php").get();
				//doc = Jsoup.parse(doc.toString()); // is it already a string?
				//writer.write(doc.select());
				
				/**
				//URL url = new URL("https://www.radiodanz.com/playlist/nowPlaying.php?randval="+ Math.random());
				URL url = new URL("https://www.radiodanz.com/index.php/last-played");	
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				boolean isError = con.getResponseCode() >= 400;
				InputStream is = null;
			    is = isError ? con.getErrorStream() : con.getInputStream();
			    BufferedReader br = new BufferedReader(new InputStreamReader(is));
			    **/

				Document doc = Jsoup.connect("https://www.radiodanz.com/playlist/nowPlaying.php?randval="+ Math.random()).get(); 
				String textContents = doc.getElementsByTag("verticalAlign").first().text();
				
				String strippedLines = textContents;
				/**
				String line = "";
				while (line != null)
				{
					line =  java.net.URLDecoder.decode(br.readLine(), "UTF-8");
					strippedLines = strippedLines + line;
				}
				**/
				
				strippedLines = strippedLines.substring(strippedLines.indexOf("verticalAlign") + 2, strippedLines.indexOf("verticalAlign") + 72);
				strippedLines = strippedLines.substring(strippedLines.indexOf("<strong>") + 1, strippedLines.lastIndexOf("<br />") - 1); // fiddle with constants
				strippedLines = strippedLines.replace("<strong>", "").replace("<br />", "");
				//parent.setVisible(true);

				if (JOptionPane.showConfirmDialog(null, "Would you like to save this song's information?", "Request", 
		    		JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
				{
					//make a popup with radio buttons asking for music genre
		 			writer.write(strippedLines + " " + "\n"); //blank to be filled with genre
				}
				//parent.setVisible(false);

		        } catch (IOException e) {
		        }
			try {
				Thread.sleep(60000);
			} catch (InterruptedException e) {
			}
		}
	}
}
