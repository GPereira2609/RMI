package br.ufal.aracomp.cosmos.config;

import br.ufal.aracomp.cosmos.connector.SimpleConnector;
import br.ufal.aracomp.cosmos.emprestimo.impl.ComponentFactory;
import br.ufal.aracomp.cosmos.emprestimo.spec.dt.DTUsuario;
import br.ufal.aracomp.cosmos.emprestimo.spec.prov.IEmprestimoOps;
import br.ufal.aracomp.cosmos.emprestimo.spec.prov.IManager;
import br.ufal.aracomp.cosmos.limite.spec.prov.ILimiteOps;

public class Main {
	public static void main(String[] args) {
		// Configuração arquitetural
		// Instância do limite
		br.ufal.aracomp.cosmos.limite.spec.prov.IManager compLimite = br.ufal.aracomp.cosmos.limite.impl.ComponentFactory.createInstance(); 
		// Instância do conector
		SimpleConnector conector = new SimpleConnector((ILimiteOps)compLimite.getProvidedInterface("ILimiteOps"));
		// Instância do empréstimo
		IManager compEmprestimo = ComponentFactory.createInstance();
		compEmprestimo.setRequiredInterface("ILimiteReq", conector);
		IEmprestimoOps empOps = (IEmprestimoOps)compEmprestimo.getProvidedInterface("IEmprestimoOps");
		
		DTUsuario usuario = new DTUsuario("5000");
		double valor = empOps.liberarEmprestimoAutomatico(usuario);
		
		System.out.println("Valor do limite: " + valor);
	}
}
