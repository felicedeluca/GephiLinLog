/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gephitools;


import java.io.File;
import org.gephi.graph.api.GraphController;
import org.gephi.graph.api.GraphModel;
import org.gephi.io.importer.api.Container;
import org.gephi.io.importer.api.EdgeDirectionDefault;
import org.gephi.io.importer.api.ImportController;
import org.gephi.io.processor.plugin.DefaultProcessor;
import org.gephi.project.api.ProjectController;
import org.gephi.project.api.Workspace;
import org.openide.util.Lookup;
import org.openide.util.Utilities;


/**
 *
 * @author Felice De Luca
 */
public class Importer {
    
    
    
    public GraphModel readGraph(Workspace workspace, String fileName){
        
        //Get controllers and models
        ImportController importController = Lookup.getDefault().lookup(ImportController.class);

        //Import file
        Container container;
        try {
           
            File test = new File(fileName);
            if(test.exists()){
                System.out.println("Exists");
            }
            else{
                System.out.println("not exist: " + test.getAbsolutePath());
            }
            
            
            //File file = Utilities.toFile(getClass().getResource(fileName).toURI());
            container = importController.importFile(test);
            container.getLoader().setEdgeDefault(EdgeDirectionDefault.DIRECTED);   //Force DIRECTED
            container.getLoader().setAllowAutoNode(false);  //Don't create missing nodes
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

        //Append imported data to GraphAPI
        importController.process(container, new DefaultProcessor(), workspace);
        
        GraphController  graphController = Lookup.getDefault().lookup(GraphController.class);
        GraphModel graphModel  = graphController.getGraphModel(workspace);
        
        return graphModel;
        
    }
    
    
}
