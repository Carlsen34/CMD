import java.util.Stack;

public class AnalisadorLexico {
	

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
		System.out.println(aux);

		
		return aux;

		
	}
	
	public static Stack consumirEspaco(Stack caracter) {
		Stack aux = new Stack();
		
		for(int i = 0;i<caracter.size();i++) {
			if(!caracter.get(i).equals(' ')) {
				aux.add(caracter.get(i));
				System.out.println(caracter.get(i));			
			}
			
		}
		
		
		System.out.println(aux);
		return aux;

	}
	
	public static Stack pegaToken(Stack caracter) {
		
		for(int i = 0; i<caracter.size();i++) {
			String aux = caracter.get(i).toString();
			if(isDigit(aux) == false) {
				System.out.println("METODO TRATAR LETRA");
			}else {
				System.out.println("METODO TRATAR DIGITO");
			}
		}
		
		
		return null;
	}

	private static boolean isDigit(String aux) {
		if(aux.equals("0") || aux.equals("1") || aux.equals("2") || aux.equals("3") || aux.equals("4") || aux.equals("5") || aux.equals("6") ||
				aux.equals("7") || aux.equals("8") || aux.equals("9")) {
			System.out.println(aux);
		return true;
		}
		return false;
	
}
}
