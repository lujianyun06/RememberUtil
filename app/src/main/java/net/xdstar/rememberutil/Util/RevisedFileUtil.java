package net.xdstar.rememberutil.Util;

import android.os.Environment;

import net.xdstar.rememberutil.Model.RevisedModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Calendar;

/**
 * Created by ljy on 16/11/3.
 */

public class RevisedFileUtil {
    private static RevisedFileUtil instance = null;
    private static final String filePath = Environment.getExternalStorageDirectory() + "/rememberUtil/"; //"/rememberUtil/"
    private static final String fileName = "revised_file.txt";


    public static RevisedFileUtil getInstance() {
        if (instance == null) {
            instance = new RevisedFileUtil();
        }
        return instance;
    }

    public int updateFile(RevisedModel revisedModel) throws IOException, ClassNotFoundException {
        int count = 0;
        File file = new File(filePath + fileName);
        if (!file.exists()) {
            File dir = file.getParentFile();
            if (!dir.exists()) {
                dir.mkdir();
            }
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(revisedModel);
            oos.close();
        } else {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath + fileName));
            RevisedModel oldRevisedModel = (RevisedModel) ois.readObject();
            oldRevisedModel.setDate(revisedModel.getDate());
            oldRevisedModel.setRevisedIdList(revisedModel.getRevisedIdList());
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(oldRevisedModel);
            oos.flush();
            oos.close();
            count = revisedModel.getRevisedIdList().size();
        }
        return count;
    }


    public RevisedModel getRevisedModel(){
        File file = new File(filePath + fileName);
        if (!file.exists()) {
            File dir = file.getParentFile();
            if (!dir.exists()) {
                file.mkdirs();
            }
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            ObjectOutputStream oos = null;
            try {
                oos = new ObjectOutputStream(fos);
            } catch (IOException e) {
                e.printStackTrace();
            }
            RevisedModel revisedModel = new RevisedModel();
            revisedModel.setDate(TextUtil.calendar2DateString(Calendar.getInstance()));
            try {

                oos.writeObject(revisedModel);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                oos.flush();
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(filePath + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        RevisedModel oldRevisedModel = null;
        try {
            oldRevisedModel = (RevisedModel) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return oldRevisedModel;
    }
}
