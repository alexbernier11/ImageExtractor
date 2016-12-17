import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;


public class ImageExtractor
{
    public static void main(String[] args) {
        String myString = "";
        String dir = "src/images";
        String initialImage = "https://s3.amazonaws.com/tapatalk-emoji/emoji10.png";
        ArrayList<String> images = new ArrayList<String>();
        
  
        // Read bytes from URL to the local file
        byte[] buffer = new byte[4096];
        int bytesRead = 0;
        int tens = 1;
        try{
            for(int i = 1; i <= 10; i++){
                System.out.println(initialImage);
                String downloadedFileName = initialImage.substring(initialImage.lastIndexOf("/")+1);
                
                URL url = new URL(initialImage);
                InputStream is = url.openStream();
                
                FileOutputStream fos = new FileOutputStream(dir + "/" + downloadedFileName);
                
                System.out.println("Downloading " + downloadedFileName);
                
                while ((bytesRead = is.read(buffer)) != -1)
                    fos.write(buffer,0,bytesRead);
                
                char[] datString = initialImage.toCharArray();
                
                int specialIndex = i+1;
                boolean index9 = false;
                
                if(i == 9){
                    specialIndex = tens;
                    index9 = true;
                }    
                
                datString[initialImage.lastIndexOf(i + "")] = (char)((specialIndex) + 48);
                initialImage = String.valueOf(datString);
                
                if(index9){
                    tens++;
                    i = -1;
                    initialImage = initialImage.substring(0,initialImage.lastIndexOf(".")) + "" + 0 + ".png";
                }
                
                images.add(downloadedFileName);
                fos.close();
                is.close();
                
            }
        } catch(Exception e){
            System.out.println("Done!");
            try
            {
                makeHtml(images);
            }
            catch (FileNotFoundException e1)
            {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
        
  
       
    }
    
    public static void makeHtml(ArrayList<String> imageNames) throws FileNotFoundException{
        
        FileWriter fWriter = null;
        BufferedWriter writer = null;
        try {
            fWriter = new FileWriter("fileName.html");
            writer = new BufferedWriter(fWriter);
            
            String htmlFile = "<!DOCTYPE html>\n<html>\n<body>\n";
            for(String fileName : imageNames){
                htmlFile += "<img src=\"src/images/" + fileName + "\">\n";
                htmlFile += "<br>";
            }
            htmlFile += "</body>\n</html>\n";
            
            writer.write(htmlFile);
            writer.close(); //make sure you close the writer object 
        } catch (Exception e) {
          //catch any exceptions here
        }
    }
    
    
}
