package com.identity.pages;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ReadData {

    private static String currentDirectory = new File("").getAbsolutePath();
    public ReadData() throws IOException {
    }

    public static int indexOf(String regex, String s, int index)
    {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(s);
        return matcher.find(index) ? matcher.start() : -1;
    }



    public String readInputFile(String pathname) throws IOException {

        File directoryPath = new File(pathname);
        File filesList[] = directoryPath.listFiles();
        System.out.println("List of files and directories in the specified directory:");
        Scanner sc = null;
        StringBuffer sb = new StringBuffer();
        for(File file : filesList) {
            sc= new Scanner(file);
            String input;
            while (sc.hasNextLine()) {
                input = sc.nextLine();
                sb.append(input+" ");
            }
        }
        return    sb.toString();
    }


    public String readOutputData(String pathname, String Registration) throws IOException {

        String temps = Registration.replaceAll(" ", "");
        File directoryPath = new File(pathname);
        File filesList[] = directoryPath.listFiles();
        Scanner sc = null;
        String line = "";
        String outputline = "";
        for (File file : filesList) {
            sc = new Scanner(file);
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                System.out.println(line);
                if (line.contains(temps)) {
                    outputline = line;
                }
            }
        }
        return outputline;
    }



    public List<String> matchRegistrations(String filename) throws IOException {

        String regexp ="([A-Z]{2})(\\d{2}-?)\\s?([A-Z]{3})";
        String result =  readInputFile(filename);
        Pattern pattern = Pattern.compile(regexp);
        List<String> allMatches = new ArrayList<String>();

        Matcher m = Pattern.compile(regexp)
                .matcher(result);

        while(m.find()) {        allMatches.add(m.group());
            System.out.println(m.group());
        }
        return allMatches;
    }
}
