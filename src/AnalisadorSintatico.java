import java.util.Stack;

public class AnalisadorSintatico {
	
	static Stack tokenAS = new Stack();
	
	
	
	public static void analisadorSintatico () {
		
		
		tokenAS = AnalisadorLexico.token;
		
		
		for(int i = 1; i<tokenAS.size();i += 2) {
			if(Simbulo.sprograma.equals(tokenAS.get(i))) {
				i += 2; //Ler proximo Token
				if(Simbulo.sidentificador.equals(tokenAS.get(i))) {
				i += 2; // Ler proximo Token
					if(Simbulo.sponto_vírgula.equals(tokenAS.get(i))) {
						

						i = analisarBloco(i);
						tokenAS.clear();
						
					}else System.out.println("ERROR");
				
					
					
				}else System.out.println("ERROR");
				
			}else System.out.println("ERROR");
		}
	}
	
		public static int analisarBloco(int i) {
		
			i += 2 ; // Ler Proximo Token
			
			i = analisaEtVariáveis(i);
			
			/*
				Analisa_subrotinas
				Analisa_comandos
			 */
			
			
			return i;
			
		}
		
		public static int analisaEtVariáveis(int i) {
			
			if(Simbulo.svar.equals(tokenAS.get(i))) {
				i += 2; // Ler Proximo Token
				if(Simbulo.sidentificador.equals(tokenAS.get(i))) {
					i = analisaVariaveis(i);
					//Analisa Variaveis
					
				}else System.out.println("ERROR");
				
				
			}else System.out.println("ERROR");
			
			return i;
			
		}
	
		
		public static int analisaVariaveis(int i) {
			do {
				System.out.println(tokenAS.get(i));
				i = i+2;
			}while(!Simbulo.sdoispontos.equals(tokenAS.get(i)));
			System.out.println(tokenAS.get(i));

			return i;
		}
	
}
