package br.ufal.aracomp.cosmos.connector;

import br.ufal.aracomp.cosmos.emprestimo.spec.dt.DTUsuario;
import br.ufal.aracomp.cosmos.emprestimo.spec.req.ILimiteReq;
import br.ufal.aracomp.cosmos.limite.spec.dt.DTCliente;
import br.ufal.aracomp.cosmos.limite.spec.prov.ILimiteOps;

public class SimpleConnector implements ILimiteReq{
	ILimiteOps limiteOps;
	
	public SimpleConnector(ILimiteOps limiteOps) {
		this.limiteOps = limiteOps;
	}
	
	@Override
	public double estimarLimite(DTUsuario usuario) {
		DTCliente cliente = new DTCliente(Double.parseDouble(usuario.rendimentos()));
		
		return this.limiteOps.calcularLimite(cliente);
	}
	
}
