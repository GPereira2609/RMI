package br.ufal.aracomp.cosmos.connector;

import br.ufal.aracomp.cosmos.emprestimo.spec.dt.DTUsuario;
import br.ufal.aracomp.cosmos.emprestimo.spec.req.ILimiteReq;
import br.ufal.aracomp.cosmos.limite.spec.dt.DTCliente;
import br.ufal.aracomp.cosmos.limite.spec.prov.ILimiteOps;
import br.ufal.aracomp.cosmos.limite2.spec.dt.DTCliente2;
import br.ufal.aracomp.cosmos.limite2.spec.prov.ILimiteOps2;
import br.ufal.aracomp.cosmos.limite3.spec.dt.DTCliente3;
import br.ufal.aracomp.cosmos.limite3.spec.prov.ILimiteOps3;

public class ConectorTolerancia implements ILimiteReq{
	ILimiteOps limiteOps;
	ILimiteOps2 limiteOps2;
	ILimiteOps3 limiteOps3;
	
	public ConectorTolerancia(ILimiteOps limiteOps, ILimiteOps2 limiteOps2, ILimiteOps3 limiteOps3) {
		this.limiteOps = limiteOps;
		this.limiteOps2 = limiteOps2;
		this.limiteOps3 = limiteOps3;
	}
	
	@Override
	public double estimarLimite(DTUsuario usuario) {
		DTCliente cliente = new DTCliente(Double.parseDouble(usuario.rendimentos()));
		DTCliente2 cliente2 = new DTCliente2(Double.parseDouble(usuario.rendimentos())); 
		DTCliente3 cliente3 = new DTCliente3(Double.parseDouble(usuario.rendimentos()));
		
		double limite1 = this.limiteOps.calcularLimite(cliente);
        double limite2 = this.limiteOps2.calcularLimite(cliente2);
        double limite3 = this.limiteOps3.calcularLimite(cliente3);
        
        double dif1 = Math.abs(limite1 - limite2);
        double dif2 = Math.abs(limite1 - limite3);
        double dif3 = Math.abs(limite2 - limite3);
        
        double porcentagem = 0.05;
        
        if (dif1 <= limite1 * porcentagem || dif1 <= limite2 * porcentagem ) {
        	return (limite1 + limite2) / 2.0;
        } else if (dif2 <= limite1 * porcentagem || dif2 <= limite3 * porcentagem){
        	return (limite1 + limite3) / 2.0;
        } else if (dif3 <= limite2 * porcentagem || dif3 <= limite3 * porcentagem){
        	return (limite2 + limite3) / 2.0;
        } else {
        	throw new RuntimeException("Limite indisponível");
        }
	}
	
}
