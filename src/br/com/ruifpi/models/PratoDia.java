package br.com.ruifpi.models;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class PratoDia implements Serializable, Comparable<PratoDia> {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private TipoPrato tipoPrato;

	@Temporal(TemporalType.DATE)
	private Date dataCardapio;

	@ManyToOne
	private PratoPronto pratoPronto;

	@ManyToOne
	private Sobremesa sobremesa;

	@ManyToOne
	private Funcionario funcionario;

	private double totalCaloria;

	public PratoDia() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TipoPrato getTipoPrato() {
		return tipoPrato;
	}

	public void setTipoPrato(TipoPrato tipoPrato) {
		this.tipoPrato = tipoPrato;
	}

	public Date getDataCardapio() {
		return dataCardapio;
	}

	public void setDataCardapio(Date dataCardapio) {
		this.dataCardapio = dataCardapio;
	}

	public PratoPronto getPratoPronto() {
		return pratoPronto;
	}

	public void setPratoPronto(PratoPronto pratoPronto) {
		this.pratoPronto = pratoPronto;
	}

	public Sobremesa getSobremesa() {
		return sobremesa;
	}

	public void setSobremesa(Sobremesa sobremesa) {
		this.sobremesa = sobremesa;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public double getTotalCaloria() {
		return totalCaloria;
	}

	public void setTotalCaloria(double totalCaloria) {
		this.totalCaloria = totalCaloria;
	}

	@Override
	public int compareTo(PratoDia arg0) {
		return 0;
	}

	public void ordenaPratoDiaByData(List<PratoDia> pratoDias) {
		Collections.sort(pratoDias, new Comparator<PratoDia>() {

			@Override
			public int compare(PratoDia pratoDia1, PratoDia pratoDia2) {
				return pratoDia1.getDataCardapio().compareTo(pratoDia2.getDataCardapio());
			}
		});
	}

}
