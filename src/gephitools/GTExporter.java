/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gephitools;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import org.gephi.io.exporter.api.ExportController;
import org.gephi.io.exporter.plugin.ExporterGML;
import org.gephi.project.api.Workspace;
import org.openide.util.Lookup;

/**
 *
 * @author Felice De Luca
 */
public class GTExporter {
    
    
    public void export(Workspace workspace, String filename) throws IOException{
        
        
        //Export full graph
        ExportController ec = Lookup.getDefault().lookup(ExportController.class);
        try {
            ec.exportFile(new File(filename));
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }
        
        ExporterGML gml = new ExporterGML();
        gml.setWorkspace(workspace);
        StringWriter stringWriter = new StringWriter();
        ec.exportWriter(stringWriter, gml);
        //System.out.println(stringWriter.toString());   //Uncomment this line

    }
    
}
