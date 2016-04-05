 package com.g3w16.entities.viewController;
 
import java.io.InputStream;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
 
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author 1040570
 */
@Named
@RequestScoped
public class FileDownloadView {
     
    private StreamedContent file;
     
    public FileDownloadView() {        
        InputStream stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resources/downloads/Alice_in_Wonderland.pdf");
        file = new DefaultStreamedContent(stream, "image/pdf", "downloaded_alice.pdf");
    }
 
    public StreamedContent getFile() {
        return file;
    }
}
 