package org.example;

import weka.core.Instances;
import weka.core.converters.ArffLoader;

import java.io.File;
import java.io.IOException;

public class FileController {

    public Instances readFile(String path) throws IOException {

        ArffLoader arffLoader = new ArffLoader();
        arffLoader.setFile(new File(path));

        Instances instances = arffLoader.getDataSet();

        if(instances.classIndex() == -1){
            instances.setClassIndex(instances.numAttributes() - 1);
        }

        return instances;
    }


    public void saveFile(String path, Instances instances) throws IOException {
        weka.core.converters.ArffSaver arffSaver = new weka.core.converters.ArffSaver();
        arffSaver.setInstances(instances);
        arffSaver.setFile(new File(path));
        arffSaver.writeBatch();
    }



}
