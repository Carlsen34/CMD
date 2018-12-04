import javax.swing.JOptionPane;

public class Erro {


	
	
	public static void tratarError1(int i, String tipo) {
		String aux = "";
		System.out.println(AnalisadorLexico.token);
		for(int k = 0;k<10;k = k+2) {
			if(AnalisadorSintatico.tokenAS.isEmpty()) {
				aux = aux + AnalisadorLexico.token.get(i-9 + k).toString() + " ";
			}else {
				aux = aux + AnalisadorSintatico.tokenAS.get(i-9 + k).toString() + " ";
			}
		}
		
		if(tipo.equals("lexico")) {
			JOptionPane.showMessageDialog(null,"Error Lexico" +" "+ aux);
			System.exit(0);

		}
		if(tipo.equals("sintatico")) {
			JOptionPane.showMessageDialog(null,"Error Sintatico"  +" "+ aux);
			System.exit(0);

		}
		if(tipo.equals("semantico")) {
			JOptionPane.showMessageDialog(null,"Error Semantico" +" "+ aux);
			System.exit(0);

		}
		
		
	}

}
