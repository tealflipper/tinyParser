import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import javax.swing.JFileChooser;

class TinyParserGUI extends JFrame implements ActionListener {
	
	static JButton btn_selectFile;
	static JButton btn_run;

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
		JLabel etiquetaTituloTextArea = new JLabel("Escribe tu código aquí:");
		etiquetaTituloTextArea.setForeground(Color.BLACK);
		JLabel etiquetaTituloScreen = new JLabel("Resultado:");
		etiquetaTituloScreen.setForeground(Color.BLACK);

		// create a textArea
		//este es el area de texto donde se escribe el código
		JTextArea textArea = new JTextArea(10, 45);
		textArea.setEditable(true);
		//Este otro no es editable por el usuario 
		//y sera la pantalla donde se musetra la salida del código
		JTextArea screen = new JTextArea(10, 45);
		textArea.setEditable(false);



		

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
		p.add(etiquetaTituloTextArea);
		p.add(screen);
		p.add(etiquetaTituloScreen);
		p.add(textArea);
		


		p.setBackground(Color.white);
		f.add(p);
		f.setSize(600, 600);
		f.show();
	}

	public void abrirFileChooser(ActionEvent e) {
		/*
		JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		// invoke the showsSaveDialog function to show the save dialog
		int r = j.showSaveDialog(null);
		// if the user selects a file
		if (r == JFileChooser.APPROVE_OPTION){
			// set the label to the path of the selected file
			l.setText(j.getSelectedFile().getAbsolutePath());
		}//*/
		System.out.println("ABRIR VENTANA EMERGENTE");
	}

	public void actionPerformed(ActionEvent e) {
		String s = e.getActionCommand();
		// string is being input
		// if the value is a number
		if (s == "Select file") {
			abrirFileChooser(e);
		}else{
			//Run presssed
		}
	}
}