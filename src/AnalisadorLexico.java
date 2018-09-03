import java.util.Stack;

public class AnalisadorLexico {
	static Stack token = new Stack();


	public static Stack eliminarComentario(Stack caracter) {
		Stack aux = new Stack();


		for(int i = 0; i<caracter.size();i++) {
			if(caracter.get(i).equals('{')) {
				do {
					i++;	
				}while(!caracter.get(i).equals('}'));
		}else {
			aux.add(caracter.get(i));

		}
		}

		
		return aux;

		
	}
	
	public static Stack consumirEspaco(Stack caracter) {
		Stack aux = new Stack();
		
		for(int i = 0;i<caracter.size();i++) {
			if(!caracter.get(i).equals(' ')) {
				aux.add(caracter.get(i));
			}
			
		}
		
		
		return aux;

	}
	
	public static Stack pegaToken(Stack caracter) {
		Simbulo simbulos = new Simbulo();
		for(int i = 0; i<caracter.size();i++) {
			String aux = caracter.get(i).toString();
			if(isDigit(aux) == false) {
				if(aux.equals("(")) token.add(simbulos.sabre_parênteses);
				if(aux.equals(")")) token.add(simbulos.sfecha_parênteses);
				if(aux.equals(".")) token.add(simbulos.Sponto);
				if(aux.equals(";")) token.add(simbulos.Sdoispontos);
				if(aux.equals(",")) token.add(simbulos.Svírgula);
				if(aux.equals(">")) token.add(simbulos.Smaior);
				if(aux.equals(">=")) token.add(simbulos.Smaiorig);
				if(aux.equals("=")) token.add(simbulos.Sig);



				
//				System.out.println("METODO TRATAR LETRA");
			}else {
	//			System.out.println("METODO TRATAR DIGITO");
			}
		}
		
		
		return token;
	}

	private static boolean isDigit(String aux) {
		if(aux.equals("0") || aux.equals("1") || aux.equals("2") || aux.equals("3") || aux.equals("4") || aux.equals("5") || aux.equals("6") ||
				aux.equals("7") || aux.equals("8") || aux.equals("9")) {
		return true;
		}
		return false;
	
}
}
