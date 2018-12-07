import javax.swing.JOptionPane;

public class Erro {


	
	public static void tratarError1(int i, String tipo) {
		String aux = "";
	
		
		if(tipo.equals("lexico")) {
			JOptionPane.showMessageDialog(null,"Error Lexico na Linha" +" "+ i);
			System.exit(0);

		}
		if(tipo.equals("sintatico")) {
			JOptionPane.showMessageDialog(null,"Error Sintatico:"  +" "+ AnalisadorSintatico.tokenAS.get(i-1));
			JOptionPane.showMessageDialog(null,"Error Sintatico na Linha :"  +" "+ AnalisadorSintatico.tokenASerror.get(i-1));
			System.exit(0);
		}
		if(tipo.equals("semantico")) {
			JOptionPane.showMessageDialog(null,"Error Semantico na Linha :" +" "+ AnalisadorSintatico.tokenASerror.get(i-1));
			System.exit(0);
		}
		
		
	}

}
