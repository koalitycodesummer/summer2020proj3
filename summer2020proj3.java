import java.io.*;
import java.util.*;

public class summer2020proj3 {
 static ArrayList<String[]> data = new ArrayList<String[]>();
 static ArrayList<String> states = new ArrayList<String>();
 static ArrayList<Double[]> cases = new ArrayList<Double[]>();
 static double[] average;
 static double[] percentage;
 static double total = 0;
 public static void main (String[] args){
  readFile("daily.csv");
  process();
  average = new double[states.size()];
  percentage = new double[states.size()];
  calc();
  for (int i = 0; i<percentage.length; i++){
   System.out.println("State: " + states.get(i) + " Percentage: " + percentage[i]);
  }
 }
 public static void readFile(String filename) {
  String sline;
  String[] bufs;
  FileReader frd = null;
  BufferedReader brd = null;
  try {
   frd = new FileReader(filename);
   brd = new BufferedReader(frd); 
  }
  catch (IOException e) {
   System.out.println("Can't open file to read:" + filename);
   System.exit(0);
  }
  try {
   String line = "";
   while (true){
    line = brd.readLine();
    if (line ==null){
     break;
    }
    String[] temp = line.split(",");
    data.add(temp);
   }
   brd.close();
   frd.close();
  }
  catch (IOException e) {
   System.out.println("Read error from file:" + filename);
   System.exit(0);
  }
 }
 
 public static void process(){
  String[] dates = {"20200803", "20200622", "20200522", "20200422", "20200322"};
  for (int i = 1; i<data.size(); i++){ 
   if (check_date(dates, data.get(i)) == false){
    continue;
   }
   int [] s = update_states(data.get(i)[1]);
   int index = s[1];
   int flag = s[0];
   if (flag == 0){
    double num = Double.valueOf(data.get(i)[2])*1.0;
    Double[] temp = {num, 1.0};
    cases.add(temp);
   }
   else{
     
    if (data.get(i)[2].length()==0 || data.get(i)[2] == null){
     continue;
    }
    double num = Double.valueOf(data.get(i)[2])*1.0;
    double val = cases.get(index)[0] + num;
    cases.get(index)[0] = val;
    cases.get(index)[1]++; 
   }
  }
 }
 public static boolean check_date(String[] dates, String[] data){
  String d = data[0];
  for (int i = 0; i<dates.length; i++){
   if (dates[i].equals(d) == true){
    return(true);
   }
  }
  return(false); 
 }
 public static int[] update_states(String s){
  for (int i = 0; i<states.size(); i++){
   if (states.get(i).equals(s)==true){
    int [] answer = {1, i};
    return (answer);
   }
  }
  states.add(s);
  int[] answer = {0, states.size()-1};
  return(answer); 
 }
 public static void calc(){
  double sum = 0.0;
  for (int i = 0; i<cases.size(); i++){
   double val = cases.get(i)[0];
   double num = cases.get(i)[1];
   double avg = val/num;
   average[i] = avg;
   sum = sum + avg;
  }
  total = sum;
  calc_percentage();
 }
 public static void calc_percentage(){
  for (int i = 0; i<average.length; i++){
   double p = average[i]/total * 100;
   percentage[i] = p;
  }
 }
}