/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gephitools;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.gephi.graph.api.GraphModel;
import org.gephi.layout.plugin.AutoLayout;
import org.gephi.layout.plugin.force.Displacement;
import org.gephi.layout.plugin.force.StepDisplacement;
import org.gephi.layout.plugin.forceAtlas2.ForceAtlas2;
import org.gephi.layout.plugin.forceAtlas2.ForceAtlas2Builder;
import org.gephi.layout.plugin.openord.*;
import org.gephi.layout.plugin.force.yifanHu.*;
import org.gephi.layout.spi.LayoutBuilder;
import org.gephi.project.api.ProjectController;
import org.gephi.project.api.Workspace;
import org.openide.util.Lookup;


/**
 *
 * @author Felice De Luca
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception{
        
        GTExporter gtex = new GTExporter();
        
        String filename = "/Users/felicedeluca/Desktop/Topics_Graph.dot";//args[0];
       
        //Force Atlas 2
        //gtex.export(drawForceAtlas2(filename), filename+"_LinLog_drawing_10min.gml");
        
        //OpenOrd
        gtex.export(drawOpenord(filename), filename+"_Openord_drawing_10min.gml");
        
        //Hu
        //gtex.export(drawHu(filename), filename+"_Hu_drawing_10min.gml");

               
    }
    
    
     static Workspace drawForceAtlas2(String filename){
          //Init a project - and therefore a workspace
        ProjectController pc = Lookup.getDefault().lookup(ProjectController.class);
        pc.newProject();
        Workspace workspace = pc.getCurrentWorkspace();

        Importer importer = new Importer();

        GraphModel graphModel = importer.readGraph(workspace, filename);

        ForceAtlas2 layout = new ForceAtlas2(new ForceAtlas2Builder());
        layout.setGraphModel(graphModel);
        layout.resetPropertiesValues();
        layout.setLinLogMode(true);


        layout.setEdgeWeightInfluence(1.0);
        layout.setGravity(1.0);
        layout.setScalingRatio(100.0);
        layout.setAdjustSizes(false);
        //layout.setBarnesHutTheta(10.0);
        layout.setJitterTolerance(0.1);
        layout.setThreadsCount(7);

        AutoLayout autoLayout = new AutoLayout(10, TimeUnit.MINUTES);
        autoLayout.setGraphModel(graphModel);
        autoLayout.addLayout(layout,1.0f);
        

        long startTime = System.nanoTime();
//        layout.initAlgo();
//        for (int i = 0; i < 4000 && layout.canAlgo(); i++)
//            layout.goAlgo();
//        layout.endAlgo();
        autoLayout.execute();
        long endTime   = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println("ForceAtlas2 running time: (sec)" + totalTime/1000000000.0);
        
        return workspace;
    }
     
     static Workspace drawOpenord(String filename){
          //Init a project - and therefore a workspace
        ProjectController pc = Lookup.getDefault().lookup(ProjectController.class);
        pc.newProject();
        Workspace workspace = pc.getCurrentWorkspace();

        Importer importer = new Importer();

        GraphModel graphModel = importer.readGraph(workspace, filename);

        OpenOrdLayout layout = new OpenOrdLayout(new OpenOrdLayoutBuilder());
        layout.setGraphModel(graphModel);
        layout.resetPropertiesValues();
        
        //Parameters
        layout.setLiquidStage(25);
        layout.setExpansionStage(25);
        layout.setCooldownStage(25);
        layout.setCrunchStage(10);
        layout.setSimmerStage(25);
        layout.setEdgeCut(new Float(0.8));
        layout.setNumThreads(7);
        layout.setNumIterations(750);
        layout.setRandSeed(new Random(System.currentTimeMillis()).nextLong());

//        AutoLayout autoLayout = new AutoLayout(10, TimeUnit.MINUTES);
//        autoLayout.setGraphModel(graphModel);
//        autoLayout.addLayout(layout,1.0f);
        

        long startTime = System.nanoTime();
        layout.initAlgo();
        for (int i = 0; i < 4000 && layout.canAlgo(); i++)
            layout.goAlgo();
        layout.endAlgo();
//        autoLayout.execute();
        long endTime   = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println("Openord running time: (sec)" + totalTime/ 1000000000.0);
        
        return workspace;
    }
     
     static Workspace drawHu(String filename){
          //Init a project - and therefore a workspace
        ProjectController pc = Lookup.getDefault().lookup(ProjectController.class);
        pc.newProject();
        Workspace workspace = pc.getCurrentWorkspace();

        Importer importer = new Importer();
        
        
       

        GraphModel graphModel = importer.readGraph(workspace, filename);


        YifanHuLayout layout;
        layout = new YifanHuLayout(new YifanHu(), new StepDisplacement(1));
        layout.setGraphModel(graphModel);
        layout.resetPropertiesValues();
        
        //Parameters
        layout.setOptimalDistance(new Float(100));
        layout.setRelativeStrength(new Float(0.2));
        layout.setInitialStep(new Float(20));
        layout.setAdaptiveCooling(true);
        layout.setStepRatio(new Float(0.95));
        layout.setConvergenceThreshold(new Float(1.0E-4));
        layout.setBarnesHutTheta(new Float(1.2));
        layout.setQuadTreeMaxLevel(10);
        
        
        AutoLayout autoLayout = new AutoLayout(10, TimeUnit.MINUTES);
        autoLayout.setGraphModel(graphModel);
        autoLayout.addLayout(layout,1.0f);
//        

        long startTime = System.nanoTime();
//        layout.initAlgo();
//        for (int i = 0; i < 4000 && layout.canAlgo(); i++)
//            layout.goAlgo();
//        layout.endAlgo();
        autoLayout.execute();
        long endTime   = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println("YifanHu running time:(sec) " + totalTime/ 1000000000.0);
        
        return workspace;
    }
    
    
  
    
}
