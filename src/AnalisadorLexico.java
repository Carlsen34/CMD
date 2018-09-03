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
			if(caracter.get(i).equals(' ')) {
				i++;
			}else {
				aux.add(caracter.get(i));

			}
			
		}
		
		System.out.println(aux);
		return aux;

	}
	
	
}
