package project.com.br;

import javax.persistence.Persistence;

public class TesteJPA {
	
//	@Test
//	public void testeJPAUtil() {
//		
//		ConnectionBD.getEntityManager();
//	}
	
	public static void main(String[] args) {
		
		Persistence.createEntityManagerFactory("jsfproject");
		
	}

}
