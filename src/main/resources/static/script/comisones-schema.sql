/*==============================================================*/
/* Tabla: comisiones_prestamo_cliente                           */
/*==============================================================*/
DROP TABLE IF EXISTS comisiones_prestamo_cliente;
CREATE TABLE comisiones_prestamo_cliente (
   id_comision_cliente  SERIAL NOT NULL,
   id_prestamo_cliente  INT NOT NULL,
   id_comision_prestamo INT NOT NULL,  -- Cambiado a NOT NULL
   fecha_aplicacion     DATE NOT NULL,
   monto                NUMERIC(15,2) NOT NULL,
   estado               VARCHAR(15) NOT NULL DEFAULT 'PENDIENTE'
      CONSTRAINT ckc_estado_comision_cliente CHECK (estado IN ('PENDIENTE','CANCELADA','EXENTA')),
   version              NUMERIC(9) NOT NULL DEFAULT 1,
   CONSTRAINT pk_comisiones_prestamo_cliente PRIMARY KEY (id_comision_cliente)
);

COMMENT ON TABLE comisiones_prestamo_cliente IS
'Registro de comisiones aplicadas a prestamos especificos de clientes.';

COMMENT ON COLUMN comisiones_prestamo_cliente.id_comision_cliente IS
'Identificador unico de la comision del prestamo.';

COMMENT ON COLUMN comisiones_prestamo_cliente.id_prestamo_cliente IS
'Clave foranea que referencia el prestamo del cliente.';

COMMENT ON COLUMN comisiones_prestamo_cliente.id_comision_prestamo IS
'Clave foranea que referencia la comision configurada para el producto.';

COMMENT ON COLUMN comisiones_prestamo_cliente.fecha_aplicacion IS
'Fecha en que se aplico la comision al prestamo.';

COMMENT ON COLUMN comisiones_prestamo_cliente.monto IS
'Monto aplicado de la comision.';

COMMENT ON COLUMN comisiones_prestamo_cliente.estado IS
'Estado actual de la comision (PENDIENTE, CANCELADA, EXENTA).';

COMMENT ON COLUMN comisiones_prestamo_cliente.version IS
'Version del registro para control de concurrencia.';

/*==============================================================*/
/* Tabla: comisiones_prestamos                                  */
/*==============================================================*/
DROP TABLE IF EXISTS comisiones_prestamos;
CREATE TABLE comisiones_prestamos (
   id_comision_prestamo SERIAL NOT NULL,
   id_tipo_comision     INT NOT NULL,  -- Cambiado a NOT NULL
   id_prestamo          INT NOT NULL,  -- Cambiado a NOT NULL
   estado               VARCHAR(15) NOT NULL DEFAULT 'ACTIVO'
      CONSTRAINT ckc_estado_comision_prestamo CHECK (estado IN ('ACTIVO','INACTIVO')),
   version              NUMERIC(9) NOT NULL DEFAULT 1,
   CONSTRAINT pk_comisiones_prestamos PRIMARY KEY (id_comision_prestamo)
);

COMMENT ON TABLE comisiones_prestamos IS
'Configuracion de comisiones aplicadas a nivel de producto de prestamo.';

COMMENT ON COLUMN comisiones_prestamos.id_comision_prestamo IS
'Identificador unico de la configuracion comision-prestamo.';

COMMENT ON COLUMN comisiones_prestamos.id_tipo_comision IS
'Clave foranea que referencia el tipo de comision.';

COMMENT ON COLUMN comisiones_prestamos.id_prestamo IS
'Clave foranea que referencia el producto de prestamo.';

COMMENT ON COLUMN comisiones_prestamos.estado IS
'Estado actual de la configuracion (ACTIVO o INACTIVO).';

COMMENT ON COLUMN comisiones_prestamos.version IS
'Version del registro para control de concurrencia.';

/*==============================================================*/
/* Tabla: tipos_comisiones                                      */
/*==============================================================*/
DROP TABLE IF EXISTS tipos_comisiones;
CREATE TABLE tipos_comisiones (
   id_tipo_comision     SERIAL NOT NULL,
   tipo                 VARCHAR(30) NOT NULL
      CONSTRAINT ckc_tipo_comision CHECK (tipo IN ('ORIGINACION','PAGO ATRASADO','PREPAGO','MODIFICACION','SERVICIO ADICIONAL')),
   nombre               VARCHAR(100) NOT NULL,
   descripcion          VARCHAR(200) NULL,
   tipo_calculo         VARCHAR(20) NOT NULL
      CONSTRAINT ckc_tipo_calculo_comision CHECK (tipo_calculo IN ('PORCENTAJE','FIJO')),
   monto                NUMERIC(15,2) NOT NULL,
   estado               VARCHAR(15) NOT NULL DEFAULT 'ACTIVO'
      CONSTRAINT ckc_estado_tipo_comision CHECK (estado IN ('ACTIVO','INACTIVO')),
   version              NUMERIC(9) NOT NULL DEFAULT 1,  -- Cambiado a DEFAULT 1
   CONSTRAINT pk_tipos_comisiones PRIMARY KEY (id_tipo_comision)
);

COMMENT ON TABLE tipos_comisiones IS
'Catalogo de tipos de comisiones o cargos aplicables a prestamos.';

COMMENT ON COLUMN tipos_comisiones.id_tipo_comision IS
'Identificador unico del tipo de comision.';

COMMENT ON COLUMN tipos_comisiones.tipo IS
'Categoria de comision (ORIGINACION, PAGO ATRASADO, PREPAGO, etc).';

COMMENT ON COLUMN tipos_comisiones.nombre IS
'Nombre descriptivo del tipo de comision.';

COMMENT ON COLUMN tipos_comisiones.descripcion IS
'Descripcion detallada de la comision y su aplicacion.';

COMMENT ON COLUMN tipos_comisiones.tipo_calculo IS
'Metodo de calculo (PORCENTAJE del monto o valor FIJO).';

COMMENT ON COLUMN tipos_comisiones.monto IS
'Valor base para el calculo (porcentaje o monto fijo).';

COMMENT ON COLUMN tipos_comisiones.estado IS
'Estado del tipo de comision (ACTIVO, INACTIVO).';

COMMENT ON COLUMN tipos_comisiones.version IS
'Version del registro para control de concurrencia.';

/*==============================================================*/
/* Relaciones                                                   */
/*==============================================================*/
ALTER TABLE comisiones_prestamo_cliente
   ADD CONSTRAINT fk_comisiones_prestamo_cliente_comisiones_prestamos 
   FOREIGN KEY (id_comision_prestamo) 
   REFERENCES comisiones_prestamos (id_comision_prestamo)
   ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE comisiones_prestamo_cliente
   ADD CONSTRAINT fk_comisiones_prestamo_cliente_prestamos_clientes 
   FOREIGN KEY (id_prestamo_cliente) 
   REFERENCES prestamos_clientes (id_prestamo_cliente)
   ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE comisiones_prestamos
   ADD CONSTRAINT fk_comisiones_prestamos_tipos_comisiones 
   FOREIGN KEY (id_tipo_comision) 
   REFERENCES tipos_comisiones (id_tipo_comision)
   ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE comisiones_prestamos
   ADD CONSTRAINT fk_comisiones_prestamos_prestamos 
   FOREIGN KEY (id_prestamo) 
   REFERENCES prestamos (id_prestamo)
   ON DELETE RESTRICT ON UPDATE RESTRICT;