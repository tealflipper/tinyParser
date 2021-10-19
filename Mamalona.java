
import java.io.File;


import javax.swing.JFileChooser;

public class Mamalona {
  
  public static void main(String[] args) throws Exception{
    Interpreter intp = new Interpreter();
    
    //--JALAR ARCHIVO DESDE MI DIRECTORIO --
    File archivo;
    JFileChooser seleccionar;
    seleccionar = new JFileChooser();
    seleccionar.showOpenDialog(null);
    archivo = seleccionar.getSelectedFile();
    
    //--LEER EL ARCHIVO PARA MANDAR A INTERPRETE
    String direccion = seleccionar.getCurrentDirectory()+"/"+archivo.getName().toString();
    
    
    intp.interpret(direccion);
  }


}
