import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import javax.swing.JFileChooser;
import javax.swing.text.BadLocationException;

class TinyParserGUI extends JFrame implements ActionListener {
	
	static JButton btn_selectFile;
	static JButton btn_run;
	static JTextArea textArea;
	static JTextArea screen;

	static String direccion;
	// create a frame
	static JFrame f;

	// create a textfield
	static JTextField l;

	

	// store operator and operands
	String inputString;
	String outputString;
	String curString;

	// default constructor
	TinyParserGUI() {
		inputString = outputString = curString = "";
	}

	// main function
	public static void main(String args[]) {
		// create a frame
		f = new JFrame("TinyParser");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		try {
			// set look and feel
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		// create a object of class
		TinyParserGUI tinyParser = new TinyParserGUI();

		//Etiquetas de titulo
		JLabel etiquetaVacia = new JLabel("                                                                                                                  ");
		JLabel etiquetaTituloTextArea = new JLabel("Archivo");
		etiquetaTituloTextArea.setForeground(Color.BLACK);
		JLabel etiquetaTituloScreen = new JLabel("Resultado:");
		etiquetaTituloScreen.setForeground(Color.BLACK);

		// create a textArea
		//este es el area de texto donde se escribe el código
		textArea = new JTextArea(10, 45);
		textArea.setEditable(true);
		//Este otro no es editable por el usuario 
		//y sera la pantalla donde se musetra la salida del código
		screen = new JTextArea(10, 45);
		textArea.setEditable(false);
		screen.setEditable(false);
		

		PrintStream printStream = new PrintStream(new CustomOutputStream(textArea));
	    System.setOut(printStream);
        System.setErr(printStream);

		 
        

		// create a panel
		JPanel p = new JPanel();


		
        //se ceran los botones para correr el codigo y seleccionar el archivo
		btn_run = new JButton("Run");
		btn_selectFile = new JButton("Select file");
        btn_run.addActionListener(tinyParser);
		btn_selectFile.addActionListener(tinyParser);

		p.add(btn_run);
		p.add(btn_selectFile);
		//add elements to panel
        //Agregamos el area de texto
        
		p.add(etiquetaVacia);
		//p.add(etiquetaTituloTextArea);
		p.add(screen);
		p.add(etiquetaTituloScreen);
		p.add(textArea);
		


		p.setBackground(Color.white);
		f.add(p);
		f.setSize(600, 600);
		f.show();
	}

	public void abrirFileChooser(ActionEvent e) {
		
		//System.out.println("ABRIR VENTANA EMERGENTE");

		/*JALAR ARCHIVO DESDE MI DIRECTORIO*/

    	File archivo;
    	JFileChooser seleccionar;
    	seleccionar = new JFileChooser();
    	seleccionar.showOpenDialog(null);
    	archivo = seleccionar.getSelectedFile();
    
    	//--LEER EL ARCHIVO PARA MANDAR A INTERPRETE
    	direccion = seleccionar.getCurrentDirectory()+"/"+archivo.getName().toString();
    
		//--PEGAR TEXTO
		String texto = leerArchivo(direccion);
		screen.setText(texto);

		//--LIMPIA TEXT AREA
		try {
			textArea.getDocument().remove(0,textArea.getDocument().getLength());
			//standardOut.println("Text area cleared");
		} catch (BadLocationException ex) {
			ex.printStackTrace();
		}

	}

	public void ejecutar(ActionEvent e) throws Exception{
		//System.out.println("ABRIR VENTANA EJECUTAR");
		Interpreter intp = new Interpreter();
		intp.interpret(direccion);



	}

	public void actionPerformed(ActionEvent e)  {
		String s = e.getActionCommand();
		// string is being input
		// if the value is a number
		if (s == "Select file") {
			abrirFileChooser(e);
		}else{
			try{
				ejecutar(e);
			}catch(Exception ex){
				System.err.println("Error en el parse");
			}
			
		}
	}

	public String leerArchivo(String direccion){
		String texto = "";
		try{
			BufferedReader bf = new BufferedReader(new FileReader(direccion));
			String temp = "";
			String bfRead;

			while((bfRead = bf.readLine()) != null){
				temp = temp + bfRead + "\n";
			}

			texto = temp;

		}catch(Exception e){
			System.err.println("Error al leer el archivo");
		}

		return texto;
	}
}