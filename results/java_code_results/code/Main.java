package org.example;

import weka.attributeSelection.*;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.lazy.IBk;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.*;

import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws Exception {

        FileController fileController = new FileController();
        String trainDataPath = "data/KDDTrain+.arff";
        String testDataPath = "data/KDDTest+.arff";
        Instances trainSet = fileController.readFile(trainDataPath);
        Instances testSet = fileController.readFile(testDataPath);


        Instances[] fsData = applyFeatureSelection(preProcessData(trainSet,"n"), preProcessData(testSet,"n"));

        TestModel(fsData[0],fsData[1]);





    }


    public static Instances handleCategoricalData(Instances data) throws Exception{

        NominalToBinary nominalToBinary = new NominalToBinary();
        nominalToBinary.setInputFormat(data);
        return Filter.useFilter(data, nominalToBinary);

    }

    public static Instances handleMissingValues(Instances data) throws Exception{

        ReplaceMissingValues replaceMissingValues = new ReplaceMissingValues();
        replaceMissingValues.setInputFormat(data);
        return Filter.useFilter(data, replaceMissingValues);

    }


    public static Instances preProcessData(Instances data, String type) throws Exception{

        //Step1: Handle Categorical Data
        data = handleCategoricalData(data);

        //Step2: Handle Missing Values
        data = handleMissingValues(data);

        //Step3: Normalize or Standardize Data

        if(type.equals("s")) {
            return DiscretizeData(StandardizeData(data));
        }


        return DiscretizeData(NormalizeData(data));



    }


    public static Instances DiscretizeData(Instances data) throws Exception{

        Discretize discretize = new Discretize();
        discretize.setInputFormat(data);
        return Filter.useFilter(data, discretize);

    }


    public static Instances StandardizeData(Instances data) throws Exception{

        Standardize standardize = new Standardize();
        standardize.setInputFormat(data);
        return Filter.useFilter(data, standardize);

    }

    public static Instances NormalizeData(Instances data) throws Exception{

        Normalize normalize = new Normalize();
        normalize.setInputFormat(data);
        return Filter.useFilter(data, normalize);

    }


    public static Instances[] applyFeatureSelection(Instances train,Instances test) throws Exception {



        Instances[] data = new Instances[2];
        AttributeSelection attributeSelection = new AttributeSelection();



        attributeSelection.setEvaluator(new CfsSubsetEval());
        attributeSelection.setSearch(new BestFirst());
        attributeSelection.SelectAttributes(train);


        data[0] = attributeSelection.reduceDimensionality(train);
        data[1] = attributeSelection.reduceDimensionality(test);

        System.out.println("Selected Attributes:-----");
        for(int i=0;i<data[0].numAttributes();i++){
            System.out.println(data[0].attribute(i));
        }
        System.out.println("-------------------------------------------------");
        return data;

    }



    public static void TestModel(Instances trainData, Instances testData) throws Exception{






        Classifier j48 = new J48();
        j48.buildClassifier(trainData);
        Evaluation evalJ48 = new Evaluation(trainData);
        evalJ48.evaluateModel(j48, testData);
        Print("J48",evalJ48);






        Classifier Nb = new NaiveBayes();
        Nb.buildClassifier(trainData);
        Evaluation evalNb = new Evaluation(trainData);
        evalNb.evaluateModel(Nb, testData);
        Print("NB",evalNb);






        Classifier rf = new RandomForest();
        rf.buildClassifier(trainData);
        Evaluation evalRf = new Evaluation(trainData);
        evalRf.evaluateModel(rf, testData);
        Print("RandomForest",evalRf);



        IBk Knn = new IBk();
        Knn.buildClassifier(trainData);
        Evaluation evalKnn = new Evaluation(trainData);
        evalKnn.evaluateModel(Knn, testData);
        Print("KNN",evalKnn);



        MultilayerPerceptron nn = new MultilayerPerceptron();
        nn.setLearningRate(0.1);
        nn.setMomentum(0.2);
        nn.setTrainingTime(200);
        nn.setHiddenLayers("3");
        nn.buildClassifier(trainData);
        Evaluation evalNn = new Evaluation(trainData);
        evalNn.evaluateModel(nn, testData);
        Print("NN",evalNn);






    }


    public static void Print(String name, Evaluation evaluation) throws Exception {
        System.out.println(name+":---------- \n"+evaluation.toSummaryString());
        System.out.println("Accuracy: "+evaluation.pctCorrect());
        System.out.println("Precision_0: "+evaluation.precision(0));
        System.out.println("Precision_1: "+evaluation.precision(1));
        System.out.println("F1_0: "+evaluation.fMeasure(0));
        System.out.println("F1_1: "+evaluation.fMeasure(1));



        System.out.println(evaluation.toMatrixString("Confusion Matrix: "));

        

    }



}
