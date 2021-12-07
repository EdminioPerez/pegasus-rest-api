/* AssentSoftware (C)2021 */
package com.greek.service.data.utils;

import com.gvt.data.transformer.ContraintsNameResolver;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class CustomConstraintNameResolver implements ContraintsNameResolver {

    private static Map<String, String> constraintCodeMap = new HashMap<>();

    static {
		constraintCodeMap.put("uq_cedula_id_categoria_persona_id_organizacion",
				"exception.document.identification.exists");
		constraintCodeMap.put("fk_id_ubicacion_geografica_nacimiento_from_persona",
				"exception.geographic.location.not.exists");
		constraintCodeMap.put("fk_id_profesion_from_persona", "exception.profession.not.exists");
		constraintCodeMap.put("fk_id_tipo_sangre_from_persona", "exception.blood.group.not.exists");
		constraintCodeMap.put("fk_id_sexo_from_persona", "exception.sex.not.exists");
		constraintCodeMap.put("fk_id_codigo_postal_from_persona", "exception.postal.code.not.exists");
		constraintCodeMap.put("fk_id_tipo_documento_identificacion_from_persona",
				"exception.identity.document.type.not.exists");
		constraintCodeMap.put("meals_unique_user_datetime_idx", "exception.meals.duplicate_datetime");
		constraintCodeMap.put("uq_person_global_identification", "exception.person.already.exists");
	}

    @Override
    public Optional<Entry<String, String>> getConstraintName(String constraintMessage) {
        if (StringUtils.isNotBlank(constraintMessage)) {
            return constraintCodeMap.entrySet().stream()
                    .filter(it -> constraintMessage.contains(it.getKey()))
                    .findFirst();
        }

        return Optional.empty();
    }
}
