package com.crudCliente.crudCliente.repository.spec;

import com.crudCliente.crudCliente.model.Cliente;
import com.crudCliente.crudCliente.repository.filter.ClienteFilter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClienteSpec {

	public static Specification<Cliente> withFilter(ClienteFilter filter) {
		return (root, query, builder) -> {

			List<Predicate> predicates = new ArrayList<>();

			if (StringUtils.isNotBlank(filter.getCpf())) {
				predicates.add(builder.like(builder.upper(root.get("cpf")),  "%" + filter.getCpf().toUpperCase() + "%"));
			}

			if (StringUtils.isNotBlank(filter.getNome())) {
				predicates.add(builder.like(builder.upper(root.get("nome")), "%" + filter.getNome().toUpperCase() + "%"));
			}
			
			if (Objects.nonNull(filter.getDataCriacao())) {
				predicates.add(builder.equal(root.get("dataCriacao"), filter.getDataCriacao()));
			}

			if (Objects.nonNull(filter.getDataNascimento())) {
				predicates.add(builder.equal(root.get("dataNascimento"), filter.getDataCriacao()));
			}

			query.orderBy(builder.desc(root.get("nome")));
			return builder.and(predicates.toArray(new Predicate[0]));
		};
	}
	
	
}
