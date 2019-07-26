package com.msas.MSAS.DomainModel.Access;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ACCES_REELS")
public class AccesReel extends OperationAcces {

	private TypeAcces typeAcces;

	public TypeAcces getTypeAcces() {
		return typeAcces;
	}

	public void setTypeAcces(TypeAcces typeAcces) {
		this.typeAcces = typeAcces;
	}

	public static enum TypeAcces {
		ENTREE, SORTIE;

		@Override
		public String toString() {
			if (this.equals(TypeAcces.ENTREE))
				return "Entrée";
			else if (this.equals(SORTIE))
				return "Sortie";

			return "";
		}
	}
}
