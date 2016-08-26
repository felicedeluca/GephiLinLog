/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gephitools;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.gephi.graph.api.GraphModel;
import org.gephi.layout.plugin.forceAtlas2.ForceAtlas2;
import org.gephi.layout.plugin.forceAtlas2.ForceAtlas2Builder;
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
        
        String filename = args[0];
        int repeat = Integer.parseInt(args[1]);
       
        
        for(int rep=0; rep<repeat; rep++){
            
            
        
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
        
        
        
        layout.initAlgo();
        for (int i = 0; i < 200 && layout.canAlgo(); i++)
            layout.goAlgo();
        
        layout.endAlgo();
        
        
        int index = filename.lastIndexOf("\\");
        String outputFileName = filename.substring(index + 1);
        outputFileName = outputFileName.substring(0, outputFileName.lastIndexOf('.'));
        
        GTExporter gtex = new GTExporter();
        gtex.export(workspace, outputFileName+"_d_"+rep+"_LinLog_drawing.gml");
        
        }
        
    }
    
}
