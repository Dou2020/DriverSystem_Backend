#!/bin/bash

# ---------------------------
# Script de creación de proyecto Java
# ---------------------------

# Variable global para el nombre del proyecto/carpeta
PROJECT_NAME="ServiceFeedback"

# Crear la carpeta principal
mkdir -p "${PROJECT_NAME}"
cd "$PROJECT_NAME" || exit


# Controller
cat <<EOL > ${PROJECT_NAME}Controller.java
package com.DriverSystem_Back.modules.${PROJECT_NAME};
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/")
@AllArgsConstructor
public class ${PROJECT_NAME}Controller {

}
EOL

#Iservice
cat <<EOL > I${PROJECT_NAME}Service.java
package com.DriverSystem_Back.modules.${PROJECT_NAME};

public interface I${PROJECT_NAME}Service {
  public ${PROJECT_NAME}Response save(${PROJECT_NAME}Request request);
  public ${PROJECT_NAME}Response update(${PROJECT_NAME}Request request);
  public ${PROJECT_NAME}Response delete(Long id);
  public ${PROJECT_NAME}Response get(${PROJECT_NAME}Request request);
  public List<${PROJECT_NAME}Response> findAll();
}
EOL



# Service
cat <<EOL > ${PROJECT_NAME}Service.java
package com.DriverSystem_Back.modules.${PROJECT_NAME};
import org.springframework.stereotype.Service;
@Service
public class ${PROJECT_NAME}Service {

}
EOL


# Repository
cat <<EOL > ${PROJECT_NAME}Repository.java
package com.DriverSystem_Back.modules.${PROJECT_NAME};
import org.springframework.data.jpa.repository.JpaRepository;

public interface ${PROJECT_NAME}Repository extends JpaRepository<${PROJECT_NAME}, Long> {
}
EOL



# Model
cat <<EOL > ${PROJECT_NAME}.java
package com.DriverSystem_Back.modules.${PROJECT_NAME};
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "work_order")
public class ${PROJECT_NAME} {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
EOL


# Crear la carpeta principal
mkdir -p "dto"
cd "dto" || exit

# response
cat <<EOL > ${PROJECT_NAME}Response.java
package com.DriverSystem_Back.modules.${PROJECT_NAME}.dto

public record ${PROJECT_NAME}Response () {
}
EOL

#request
cat <<EOL > ${PROJECT_NAME}Request.java
package com.DriverSystem_Back.modules.${PROJECT_NAME}.dto

public record ${PROJECT_NAME}Request () {
}
EOL



echo "Proyecto ${PROJECT_NAME} creado con éxito."
