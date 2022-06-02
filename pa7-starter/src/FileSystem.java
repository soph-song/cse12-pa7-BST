import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Comparator;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class FileSystem {

    BST<String, FileData> nameTree;
    BST<String, ArrayList<FileData>> dateTree;
    
    // TODO
    public FileSystem() {
        nameTree = new BST<>();
        dateTree = new BST<>();

    }


    // TODO
    public FileSystem(String inputFile) {
        nameTree = new BST<>();
        dateTree = new BST<>();
        try {
            File f = new File(inputFile);
            Scanner sc = new Scanner(f);
            while (sc.hasNextLine()) {
                String[] data = sc.nextLine().split(", ");
                String filename = data[0];
                String dir = data[1];
                String modifiedDate = data[2];
                add(filename,dir,modifiedDate);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);

        }
    }


    // TODO
    public void add(String name, String dir, String date) {
        if (name == null || dir == null || date == null) {
            return;
        }
        FileData value = new FileData(name, dir, date);


        //if file is present in system
        if (nameTree.containsKey(name)) {
            String originalDate = nameTree.get(name).lastModifiedDate;
            int comp = date.compareTo(originalDate);
            //if more recent
            if (comp > 0) {
                //replace file in nameTree
                nameTree.replace(name,value);
                //delete original file in date Tree
                for (FileData data:dateTree.get(originalDate)) {
                    if (data.name.equals(name)) {
                        dateTree.get(originalDate).remove(data);
                    }
                }
                //if file date exists already, update that entry
                if (dateTree.containsKey(date)) {
                    ArrayList<FileData> existVal = dateTree.get(date);
                    existVal.add(value);
                    dateTree.replace(date,existVal);
                }
                //if first time adding this date
                else {
                    ArrayList<FileData> Val = new ArrayList<>();
                    Val.add(value);
                    dateTree.put(date,Val);
                }
            }
            else {
                return;
            }
        }
        //if file not present in system
        else  {
            nameTree.put(name,value);
            //if file date exists already, update that entry
            if (dateTree.containsKey(date)) {
                ArrayList<FileData> existVal = dateTree.get(date);
                existVal.add(value);
                dateTree.replace(date,existVal);
            }
            //if first time adding this date
            else {
                ArrayList<FileData> Val = new ArrayList<>();
                Val.add(value);
                dateTree.put(date,Val);
            }
        }


    }




    	



    // TODO
    public ArrayList<String> findFileNamesByDate(String date) {
        if (date == null) {
            return null;
        }
        ArrayList<FileData> Files = dateTree.get(date);
        ArrayList<String> filenames = new ArrayList<>();
        for (FileData data:Files){
            filenames.add(data.name);
        }
        return filenames;

    }


    // TODO
    public FileSystem filter(String startDate, String endDate) {
        FileSystem FS = new FileSystem();
        //Get the list of all dates(keys)
        List<String> dates = dateTree.keys();
        for (String date:dates) {
            int comp_start = date.compareTo(startDate);
            int comp_end = date.compareTo(endDate);
            //if date is within this range, then add to filesystem
            if (comp_start >= 0 && comp_end < 0) {
                ArrayList<FileData> datas = dateTree.get(date);
                for (FileData data:datas) {
                    FS.add(data.name,data.dir,data.lastModifiedDate);
                }
            }
        }
        return FS;
 
    }
    
    
    // TODO
    public FileSystem filter(String wildCard) {
        FileSystem FS = new FileSystem();
        //Get the list of all dates(keys)
        List<String> names = nameTree.keys();
        for (String name:names) {
            //find out if filename contains the wild card string
            boolean boo = name.contains(wildCard);
            //if date is within this range, then add to filesystem
            if (boo == true) {
                FileData data = nameTree.get(name);
                FS.add(name,data.dir,data.lastModifiedDate);
                }
            }
        
        return FS;

    }
    
    
    // TODO
    public List<String> outputNameTree(){
        List<String> output = new ArrayList<>();
        List<String> names = nameTree.keys();
        for (String name: names) {
            FileData data = nameTree.get(name);
            String infoOfFile = name + ": " + data.toString();
            output.add(infoOfFile);

        }
        return output;

    }
    
    
    // TODO
    public List<String> outputDateTree(){
        List<String> output = new ArrayList<>();
        List<String> dates = dateTree.keys();
        for (String date: dates) {
            ArrayList<FileData> datas = dateTree.get(date);
            for (FileData data:datas){
                String infoOfFile = date + ": " + data.toString();
                output.add(infoOfFile);  
            }
        }
        return output;

    }
    
}

