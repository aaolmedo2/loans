/*==============================================================*/
/* Tabla: cronogramas_pagos                                     */
/*==============================================================*/
DROP TABLE IF EXISTS cronogramas_pagos;
CREATE TABLE cronogramas_pagos (
   id_cuota             SERIAL NOT NULL,
   id_prestamo_cliente  INT NOT NULL,  -- Cambiado a NOT NULL
   numero_cuota         INT NOT NULL,
   fecha_programada     DATE NOT NULL,
   monto_cuota          NUMERIC(15,2) NOT NULL,
   interes              NUMERIC(15,2) NOT NULL,
   comisiones           NUMERIC(15,2) NOT NULL DEFAULT 0,  -- Cambiado a NOT NULL
   seguros              NUMERIC(15,2) NOT NULL DEFAULT 0,  -- Cambiado a NOT NULL
   total                NUMERIC(15,2) NOT NULL,  -- Cambiado a NOT NULL
   saldo_pendiente      NUMERIC(15,2) NOT NULL,  -- Cambiado a NOT NULL
   estado               VARCHAR(15) NOT NULL DEFAULT 'PENDIENTE'
      CONSTRAINT ckc_estado_cronograma CHECK (estado IN ('ACTIVO','INACTIVO','PENDIENTE','PAGADO')),
   version              NUMERIC(9) NOT NULL,
   CONSTRAINT pk_cronogramas_pagos PRIMARY KEY (id_cuota)
);

COMMENT ON TABLE cronogramas_pagos IS
'Tabla que almacena el cronograma de pagos para cada prestamo de cliente.';

COMMENT ON COLUMN cronogramas_pagos.id_cuota IS
'Identificador unico de la cuota.';

COMMENT ON COLUMN cronogramas_pagos.id_prestamo_cliente IS
'Clave foranea que referencia el prestamo del cliente.';

COMMENT ON COLUMN cronogramas_pagos.numero_cuota IS
'Numero de la cuota dentro del cronograma.';

COMMENT ON COLUMN cronogramas_pagos.fecha_programada IS
'Fecha en la que la cuota esta programada para ser pagada.';

COMMENT ON COLUMN cronogramas_pagos.monto_cuota IS
'Monto total de la cuota programada.';

COMMENT ON COLUMN cronogramas_pagos.interes IS
'Monto de interes incluido en la cuota.';

COMMENT ON COLUMN cronogramas_pagos.comisiones IS
'Monto de comisiones aplicado a la cuota.';

COMMENT ON COLUMN cronogramas_pagos.seguros IS
'Monto de seguros aplicado a la cuota.';

COMMENT ON COLUMN cronogramas_pagos.total IS
'Monto total a pagar en la cuota, incluyendo capital, interes, comisiones y seguros.';

COMMENT ON COLUMN cronogramas_pagos.saldo_pendiente IS
'Saldo pendiente del prestamo despues de esta cuota.';

COMMENT ON COLUMN cronogramas_pagos.estado IS
'Estado actual de la cuota (PENDIENTE, ACTIVO, INACTIVO, PAGADO).';

COMMENT ON COLUMN cronogramas_pagos.version IS
'Numero de version del registro para control de concurrencia.';

/*==============================================================*/
/* Tabla: esquemas_amortizacion                                 */
/*==============================================================*/
DROP TABLE IF EXISTS esquemas_amortizacion;
CREATE TABLE esquemas_amortizacion (
   id_esquema           SERIAL NOT NULL,
   id_tipo_prestamo     INT NULL,
   nombre               VARCHAR(20) NOT NULL
      CONSTRAINT ckc_nombre_esquema_amortizacion CHECK (nombre IN ('FRANCES','AMERICANO','ALEMAN')),
   descripcion          VARCHAR(200) NOT NULL,
   permite_gracia       BOOLEAN NOT NULL,
   estado               VARCHAR(15) NOT NULL DEFAULT 'ACTIVO'
      CONSTRAINT ckc_estado_esquema_amortizacion CHECK (estado IN ('ACTIVO','INACTIVO')),
   version              NUMERIC(9) NOT NULL,
   CONSTRAINT pk_esquemas_amortizacion PRIMARY KEY (id_esquema),
   CONSTRAINT ak_unique_nombre_esquema UNIQUE (nombre)
);

COMMENT ON TABLE esquemas_amortizacion IS
'Tabla que define los diferentes esquemas de amortizacion de prestamos.';

COMMENT ON COLUMN esquemas_amortizacion.id_esquema IS
'Identificador unico del esquema de amortizacion.';

COMMENT ON COLUMN esquemas_amortizacion.id_tipo_prestamo IS
'Clave foranea que referencia el tipo de prestamo.';

COMMENT ON COLUMN esquemas_amortizacion.nombre IS
'Nombre del esquema de amortizacion (FRANCES, AMERICANO, ALEMAN).';

COMMENT ON COLUMN esquemas_amortizacion.descripcion IS
'Descripcion detallada del esquema de amortizacion.';

COMMENT ON COLUMN esquemas_amortizacion.permite_gracia IS
'Indica si el esquema permite periodos de gracia.';

COMMENT ON COLUMN esquemas_amortizacion.estado IS
'Estado actual del esquema (ACTIVO, INACTIVO).';

COMMENT ON COLUMN esquemas_amortizacion.version IS
'Numero de version del registro para control de concurrencia.';

/*==============================================================*/
/* Tabla: garantias                                             */
/*==============================================================*/
DROP TABLE IF EXISTS garantias;
CREATE TABLE garantias (
   id_garantia          SERIAL NOT NULL,
   tipo_garantia        VARCHAR(20) NOT NULL
      CONSTRAINT ckc_tipo_garantia CHECK (tipo_garantia IN ('HIPOTECA','PRENDARIA','PERSONAL')),
   descripcion          VARCHAR(200) NOT NULL,
   valor                NUMERIC(15,2) NOT NULL,
   estado               VARCHAR(15) NOT NULL
      CONSTRAINT ckc_estado_garantia CHECK (estado IN ('ACTIVO','INACTIVO')),
   version              NUMERIC(9) NOT NULL,
   CONSTRAINT pk_garantias PRIMARY KEY (id_garantia)
);

COMMENT ON TABLE garantias IS
'Tabla que almacena informacion sobre las garantias asociadas a los tipos de prestamos.';

COMMENT ON COLUMN garantias.id_garantia IS
'Identificador unico de la garantia.';

COMMENT ON COLUMN garantias.tipo_garantia IS
'Tipo de garantia (HIPOTECA, PRENDARIA, PERSONAL).';

COMMENT ON COLUMN garantias.descripcion IS
'Descripcion detallada de la garantia.';

COMMENT ON COLUMN garantias.valor IS
'Valor monetario de la garantia.';

COMMENT ON COLUMN garantias.estado IS
'Estado actual de la garantia (ACTIVO, INACTIVO).';

COMMENT ON COLUMN garantias.version IS
'Numero de version del registro para control de concurrencia.';

/*==============================================================*/
/* Tabla: garantias_tipos_prestamos                             */
/*==============================================================*/
DROP TABLE IF EXISTS garantias_tipos_prestamos;
CREATE TABLE garantias_tipos_prestamos (
   id_garantia_tipo_prestamo SERIAL NOT NULL,
   id_garantia          INT NOT NULL,  -- Cambiado a NOT NULL
   id_tipo_prestamo     INT NOT NULL,  -- Cambiado a NOT NULL
   estado               VARCHAR(15) NOT NULL DEFAULT 'ACTIVO'  -- Cambiado a NOT NULL
      CONSTRAINT ckc_estado_garantia_tipo CHECK (estado IN ('ACTIVO','INACTIVO')),
   version              NUMERIC(9) NOT NULL DEFAULT 0,  -- Cambiado a NOT NULL
   CONSTRAINT pk_garantias_tipos_prestamos PRIMARY KEY (id_garantia_tipo_prestamo)
);

COMMENT ON TABLE garantias_tipos_prestamos IS
'Tabla de relacion entre garantias y tipos de prestamos.';

COMMENT ON COLUMN garantias_tipos_prestamos.id_garantia_tipo_prestamo IS
'Identificador unico de la relacion garantia-tipo prestamo.';

COMMENT ON COLUMN garantias_tipos_prestamos.id_garantia IS
'Identificador unico de la garantia.';

COMMENT ON COLUMN garantias_tipos_prestamos.id_tipo_prestamo IS
'Identificador unico del tipo de prestamo.';

COMMENT ON COLUMN garantias_tipos_prestamos.estado IS
'Estado de la relacion (ACTIVO, INACTIVO).';

COMMENT ON COLUMN garantias_tipos_prestamos.version IS
'Numero de version del registro para control de concurrencia.';

/*==============================================================*/
/* Tabla: garantias_tipos_prestamos_cliente                     */
/*==============================================================*/
DROP TABLE IF EXISTS garantias_tipos_prestamos_cliente;
CREATE TABLE garantias_tipos_prestamos_cliente (
   id_garantia_tipo_prestamo_cliente SERIAL NOT NULL,
   id_prestamo_cliente  INT NOT NULL,  -- Cambiado a NOT NULL
   id_garantia_tipo_prestamo INT NOT NULL,  -- Cambiado a NOT NULL
   monto_tasado         NUMERIC(15,2) NOT NULL,  -- Cambiado a NOT NULL
   fecha_registro       DATE NOT NULL DEFAULT CURRENT_DATE,  -- Cambiado a NOT NULL
   descripcion          VARCHAR(200) NULL,
   documento_referencia VARCHAR(100) NOT NULL,  -- Cambiado a NOT NULL
   estado               VARCHAR(15) NOT NULL DEFAULT 'ACTIVO'
      CONSTRAINT ckc_estado_garantia_cliente CHECK (estado IN ('ACTIVO','INACTIVO')),
   version              NUMERIC(9) NOT NULL,
   CONSTRAINT pk_garantias_tipos_prestamos_cliente PRIMARY KEY (id_garantia_tipo_prestamo_cliente)
);

COMMENT ON TABLE garantias_tipos_prestamos_cliente IS
'Tabla de relacion entre garantias de tipos de prestamos y prestamos de clientes.';

COMMENT ON COLUMN garantias_tipos_prestamos_cliente.id_garantia_tipo_prestamo_cliente IS
'Identificador unico de la relacion garantia-prestamo-cliente.';

COMMENT ON COLUMN garantias_tipos_prestamos_cliente.id_prestamo_cliente IS
'Identificador unico del prestamo del cliente.';

COMMENT ON COLUMN garantias_tipos_prestamos_cliente.id_garantia_tipo_prestamo IS
'Identificador de la relacion garantia-tipo prestamo.';

COMMENT ON COLUMN garantias_tipos_prestamos_cliente.monto_tasado IS
'Valor tasado de la garantia por un perito.';

COMMENT ON COLUMN garantias_tipos_prestamos_cliente.fecha_registro IS
'Fecha de registro de la garantia para el prestamo.';

COMMENT ON COLUMN garantias_tipos_prestamos_cliente.descripcion IS
'Descripcion detallada de la garantia asociada.';

COMMENT ON COLUMN garantias_tipos_prestamos_cliente.documento_referencia IS
'Documento que respalda la garantia (ej. escritura, contrato).';

COMMENT ON COLUMN garantias_tipos_prestamos_cliente.estado IS
'Estado de la garantia (ACTIVO, INACTIVO).';

COMMENT ON COLUMN garantias_tipos_prestamos_cliente.version IS
'Numero de version del registro para control de concurrencia.';

/*==============================================================*/
/* Tabla: pagos_prestamos                                       */
/*==============================================================*/
DROP TABLE IF EXISTS pagos_prestamos;
CREATE TABLE pagos_prestamos (
   id_pago              SERIAL NOT NULL,
   id_cuota             INT NOT NULL,  -- Cambiado a NOT NULL
   fecha_pago           DATE NOT NULL,
   monto_pagado         NUMERIC(15,2) NOT NULL,
   interes_pagado       NUMERIC(15,2) NOT NULL,
   mora_pagada          NUMERIC(15,2) NOT NULL DEFAULT 0,
   capital_pagado       NUMERIC(15,2) NOT NULL,  -- Cambiado a NOT NULL
   tipo_pago            VARCHAR(20) NOT NULL,
   referencia           VARCHAR(100) NOT NULL,
   estado               VARCHAR(15) NOT NULL DEFAULT 'COMPLETADO'
      CONSTRAINT ckc_estado_pago CHECK (estado IN ('COMPLETADO','REVERTIDO')),
   version              NUMERIC(9) NOT NULL,
   CONSTRAINT pk_pagos_prestamos PRIMARY KEY (id_pago)
);

COMMENT ON TABLE pagos_prestamos IS
'Tabla que registra los pagos realizados para las cuotas de los prestamos.';

COMMENT ON COLUMN pagos_prestamos.id_pago IS
'Identificador unico del pago.';

COMMENT ON COLUMN pagos_prestamos.id_cuota IS
'Clave foranea que referencia la cuota pagada.';

COMMENT ON COLUMN pagos_prestamos.fecha_pago IS
'Fecha en la que se realizo el pago.';

COMMENT ON COLUMN pagos_prestamos.monto_pagado IS
'Monto total pagado en esta transaccion.';

COMMENT ON COLUMN pagos_prestamos.interes_pagado IS
'Monto de interes cubierto por el pago.';

COMMENT ON COLUMN pagos_prestamos.mora_pagada IS
'Monto de mora pagado en esta transaccion.';

COMMENT ON COLUMN pagos_prestamos.capital_pagado IS
'Monto de capital cubierto por el pago.';

COMMENT ON COLUMN pagos_prestamos.tipo_pago IS
'Tipo de pago realizado (EFECTIVO, TRANSFERENCIA, TARJETA).';

COMMENT ON COLUMN pagos_prestamos.referencia IS
'Numero de referencia o identificador de la transaccion.';

COMMENT ON COLUMN pagos_prestamos.estado IS
'Estado del pago (COMPLETADO, REVERTIDO).';

COMMENT ON COLUMN pagos_prestamos.version IS
'Numero de version del registro para control de concurrencia.';

/*==============================================================*/
/* Tabla: tipos_prestamos                                       */
/*==============================================================*/
DROP TABLE IF EXISTS tipos_prestamos;
CREATE TABLE tipos_prestamos (
   id_tipo_prestamo     SERIAL NOT NULL,
   id_moneda            VARCHAR(3) NOT NULL,  -- Cambiado a NOT NULL
   nombre               VARCHAR(50) NOT NULL,
   descripcion          VARCHAR(100) NOT NULL,
   requisitos           VARCHAR(300) NOT NULL,
   tipo_cliente         VARCHAR(15) NOT NULL
      CONSTRAINT ckc_tipo_cliente CHECK (tipo_cliente IN ('PERSONA','EMPRESA','AMBOS')),
   fecha_creacion       TIMESTAMP NOT NULL DEFAULT CURRENT_DATE,
   fecha_modificacion   TIMESTAMP NOT NULL DEFAULT CURRENT_DATE,  -- Agregado DEFAULT
   estado               VARCHAR(15) NOT NULL DEFAULT 'ACTIVO'
      CONSTRAINT ckc_estado_tipo_prestamo CHECK (estado IN ('ACTIVO','INACTIVO')),
   version              NUMERIC(9) NOT NULL,
   CONSTRAINT pk_tipos_prestamos PRIMARY KEY (id_tipo_prestamo)
);

COMMENT ON TABLE tipos_prestamos IS
'Tabla que define las categorias o tipos generales de prestamos.';

COMMENT ON COLUMN tipos_prestamos.id_tipo_prestamo IS
'Identificador unico del tipo de prestamo.';

COMMENT ON COLUMN tipos_prestamos.id_moneda IS
'Moneda predeterminada para este tipo de prestamo.';

COMMENT ON COLUMN tipos_prestamos.nombre IS
'Nombre del tipo de prestamo (Personal, Hipotecario, etc).';

COMMENT ON COLUMN tipos_prestamos.descripcion IS
'Descripcion del tipo de prestamo.';

COMMENT ON COLUMN tipos_prestamos.requisitos IS
'Requisitos necesarios para solicitar este tipo de prestamo.';

COMMENT ON COLUMN tipos_prestamos.tipo_cliente IS
'Tipo de cliente permitido (PERSONA, EMPRESA, AMBOS).';

COMMENT ON COLUMN tipos_prestamos.fecha_creacion IS
'Fecha de creacion del registro.';

COMMENT ON COLUMN tipos_prestamos.fecha_modificacion IS
'Fecha de ultima modificacion del registro.';

COMMENT ON COLUMN tipos_prestamos.estado IS
'Estado del tipo de prestamo (ACTIVO, INACTIVO).';

COMMENT ON COLUMN tipos_prestamos.version IS
'Numero de version del registro para control de concurrencia.';

/*==============================================================*/
/* Tabla: prestamos                                             */
/*==============================================================*/
DROP TABLE IF EXISTS prestamos;
CREATE TABLE prestamos (
   id_prestamo          SERIAL NOT NULL,
   id_tipo_prestamo     INT NOT NULL,
   id_moneda            VARCHAR(3) NOT NULL,  -- Cambiado a NOT NULL
   nombre               VARCHAR(100) NOT NULL,
   descripcion          VARCHAR(200) NOT NULL,
   fecha_modificacion   TIMESTAMP NOT NULL DEFAULT CURRENT_DATE,
   base_calculo         VARCHAR(10) NOT NULL
      CONSTRAINT ckc_base_calculo CHECK (base_calculo IN ('30/360','31/365')),
   tasa_interes         NUMERIC(5,2) NOT NULL,
   monto_minimo         NUMERIC(15,2) NOT NULL,  -- Cambiado a NOT NULL
   monto_maximo         NUMERIC(15,2) NOT NULL,  -- Cambiado a NOT NULL
   plazo_minimo_meses   NUMERIC(2) NOT NULL,  -- Cambiado a INT y NOT NULL
   plazo_maximo_meses   NUMERIC(2) NOT NULL,  -- Cambiado a INT y NOT NULL
   tipo_amortizacion    VARCHAR(20) NOT NULL,  -- Cambiado a NOT NULL
   estado               VARCHAR(15) NOT NULL DEFAULT 'SOLICITADO'
      CONSTRAINT ckc_estado_prestamo CHECK (estado IN ('ACTIVO','INACTIVO','SOLICITADO')),
   version              NUMERIC(9) NOT NULL,
   CONSTRAINT pk_prestamos PRIMARY KEY (id_prestamo)
);

COMMENT ON TABLE prestamos IS
'Tabla que define los productos de prestamo disponibles.';

COMMENT ON COLUMN prestamos.id_prestamo IS
'Identificador unico del producto de prestamo.';

COMMENT ON COLUMN prestamos.id_tipo_prestamo IS
'Clave foranea que referencia el tipo de prestamo general.';

COMMENT ON COLUMN prestamos.id_moneda IS
'Moneda en la que se otorga el prestamo.';

COMMENT ON COLUMN prestamos.nombre IS
'Nombre del producto de prestamo.';

COMMENT ON COLUMN prestamos.descripcion IS
'Descripcion del producto de prestamo.';

COMMENT ON COLUMN prestamos.fecha_modificacion IS
'Fecha de ultima modificacion del registro.';

COMMENT ON COLUMN prestamos.base_calculo IS
'Base de calculo para intereses (30/360, 31/365).';

COMMENT ON COLUMN prestamos.tasa_interes IS
'Tasa de interes nominal para este producto.';

COMMENT ON COLUMN prestamos.monto_minimo IS
'Monto minimo que se puede solicitar.';

COMMENT ON COLUMN prestamos.monto_maximo IS
'Monto maximo que se puede solicitar.';

COMMENT ON COLUMN prestamos.plazo_minimo_meses IS
'Plazo minimo en meses para este prestamo.';

COMMENT ON COLUMN prestamos.plazo_maximo_meses IS
'Plazo maximo en meses para este prestamo.';

COMMENT ON COLUMN prestamos.tipo_amortizacion IS
'Tipo de amortizacion predeterminado (FRANCES, AMERICANO, ALEMAN).';

COMMENT ON COLUMN prestamos.estado IS
'Estado del producto (ACTIVO, INACTIVO, SOLICITADO).';

COMMENT ON COLUMN prestamos.version IS
'Numero de version del registro para control de concurrencia.';

/*==============================================================*/
/* Tabla: prestamos_clientes                                    */
/*==============================================================*/
DROP TABLE IF EXISTS prestamos_clientes;
CREATE TABLE prestamos_clientes (
   id_prestamo_cliente  SERIAL NOT NULL,
   id_cliente           INT NOT NULL,
   id_prestamo          INT NOT NULL,
   fecha_inicio         DATE NOT NULL,  -- Cambiado a NOT NULL
   fecha_aprobacion     DATE NOT NULL,
   fecha_desembolso     DATE NOT NULL,
   fecha_vencimiento    DATE NOT NULL,
   monto_solicitado     NUMERIC(15,2) NOT NULL,  -- Cambiado a NOT NULL
   plazo_meses          INT NOT NULL,  -- Cambiado a NOT NULL
   tasa_interes_aplicada NUMERIC(5,2) NOT NULL,  -- Cambiado a NOT NULL
   estado               VARCHAR(15) NOT NULL
      CONSTRAINT ckc_estado_prestamo_cliente CHECK (estado IN ('SOLICITADO','APROBADO','DESEMBOLSADO','VIGENTE','EN_MORA','REFINANCIADO','PAGADO','CASTIGADO')),
   version              NUMERIC(9) NOT NULL,
   CONSTRAINT pk_prestamos_clientes PRIMARY KEY (id_prestamo_cliente)
);

COMMENT ON TABLE prestamos_clientes IS
'Tabla que registra los prestamos otorgados a clientes.';

COMMENT ON COLUMN prestamos_clientes.id_prestamo_cliente IS
'Identificador unico del prestamo del cliente.';

COMMENT ON COLUMN prestamos_clientes.id_cliente IS
'Identificador del cliente asociado.';

COMMENT ON COLUMN prestamos_clientes.id_prestamo IS
'Clave foranea que referencia el producto de prestamo.';

COMMENT ON COLUMN prestamos_clientes.fecha_inicio IS
'Fecha de inicio del prestamo.';

COMMENT ON COLUMN prestamos_clientes.fecha_aprobacion IS
'Fecha de aprobacion del prestamo.';

COMMENT ON COLUMN prestamos_clientes.fecha_desembolso IS
'Fecha en que se desembolso el monto del prestamo.';

COMMENT ON COLUMN prestamos_clientes.fecha_vencimiento IS
'Fecha de vencimiento final del prestamo.';

COMMENT ON COLUMN prestamos_clientes.monto_solicitado IS
'Monto total solicitado por el cliente.';

COMMENT ON COLUMN prestamos_clientes.plazo_meses IS
'Plazo del prestamo en meses.';

COMMENT ON COLUMN prestamos_clientes.tasa_interes_aplicada IS
'Tasa de interes aplicada al prestamo.';

COMMENT ON COLUMN prestamos_clientes.estado IS
'Estado actual del prestamo (SOLICITADO, APROBADO, DESEMBOLSADO, VIGENTE, EN_MORA, REFINANCIADO, PAGADO, CASTIGADO).';

COMMENT ON COLUMN prestamos_clientes.version IS
'Numero de version del registro para control de concurrencia.';

/*==============================================================*/
/* Tabla: seguros                                               */
/*==============================================================*/
DROP TABLE IF EXISTS seguros;
CREATE TABLE seguros (
   id_seguro            SERIAL NOT NULL,
   tipo_seguro          VARCHAR(30) NOT NULL
      CONSTRAINT ckc_tipo_seguro CHECK (tipo_seguro IN ('VIDA','DESEMPLEO','PROTECCION_PAGOS','DESGRAVAMEN','INCENDIOS')),
   compania             VARCHAR(100) NOT NULL,
   monto_asegurado      NUMERIC(15,2) NOT NULL,
   fecha_inicio         DATE NOT NULL,
   fecha_fin            DATE NOT NULL,
   estado               VARCHAR(15) NOT NULL DEFAULT 'ACTIVO'
      CONSTRAINT ckc_estado_seguro CHECK (estado IN ('ACTIVO','VENCIDO','CANCELADO','INACTIVO')),
   version              NUMERIC(9) NOT NULL,
   CONSTRAINT pk_seguros PRIMARY KEY (id_seguro)
);

COMMENT ON TABLE seguros IS
'Tabla que define los diferentes tipos de seguros asociables a prestamos.';

COMMENT ON COLUMN seguros.id_seguro IS
'Identificador unico del seguro.';

COMMENT ON COLUMN seguros.tipo_seguro IS
'Tipo de seguro (VIDA, DESEMPLEO, PROTECCION_PAGOS, DESGRAVAMEN, INCENDIOS).';

COMMENT ON COLUMN seguros.compania IS
'Compania de seguros que emite la poliza.';

COMMENT ON COLUMN seguros.monto_asegurado IS
'Monto total asegurado por esta poliza.';

COMMENT ON COLUMN seguros.fecha_inicio IS
'Fecha de inicio de la cobertura.';

COMMENT ON COLUMN seguros.fecha_fin IS
'Fecha de fin de la cobertura.';

COMMENT ON COLUMN seguros.estado IS
'Estado actual del seguro (ACTIVO, VENCIDO, CANCELADO, INACTIVO).';

COMMENT ON COLUMN seguros.version IS
'Numero de version del registro para control de concurrencia.';

/*==============================================================*/
/* Tabla: seguros_prestamos                                     */
/*==============================================================*/
DROP TABLE IF EXISTS seguros_prestamos;
CREATE TABLE seguros_prestamos (
   id_seguro_prestamo   SERIAL NOT NULL,
   id_seguro            INT NOT NULL,  -- Cambiado a NOT NULL
   id_prestamo          INT NOT NULL,  -- Cambiado a NOT NULL
   estado               VARCHAR(15) NOT NULL
      CONSTRAINT ckc_estado_seguro_prestamo CHECK (estado IN ('ACTIVO','INACTIVO')),
   version              NUMERIC(9) NOT NULL,
   CONSTRAINT pk_seguros_prestamos PRIMARY KEY (id_seguro_prestamo)
);

COMMENT ON TABLE seguros_prestamos IS
'Tabla de relacion entre seguros y productos de prestamo.';

COMMENT ON COLUMN seguros_prestamos.id_seguro_prestamo IS
'Identificador unico de la relacion seguro-prestamo.';

COMMENT ON COLUMN seguros_prestamos.id_seguro IS
'Clave foranea que referencia el seguro asociado.';

COMMENT ON COLUMN seguros_prestamos.id_prestamo IS
'Clave foranea que referencia el producto de prestamo asociado.';

COMMENT ON COLUMN seguros_prestamos.estado IS
'Estado de la relacion (ACTIVO, INACTIVO).';

COMMENT ON COLUMN seguros_prestamos.version IS
'Numero de version del registro para control de concurrencia.';

/*==============================================================*/
/* Tabla: seguros_prestamo_cliente                              */
/*==============================================================*/
DROP TABLE IF EXISTS seguros_prestamo_cliente;
CREATE TABLE seguros_prestamo_cliente (
   id_seguro_prestamo_cliente SERIAL NOT NULL,
   id_prestamo_cliente  INT NOT NULL,  -- Cambiado a NOT NULL
   id_seguro_prestamo   INT NOT NULL,  -- Cambiado a NOT NULL
   monto_total          NUMERIC(15,2) NOT NULL,  -- Cambiado a NOT NULL
   monto_cuota          NUMERIC(15,2) NOT NULL,  -- Cambiado a NOT NULL
   estado               VARCHAR(15) NOT NULL DEFAULT 'ACTIVO'
      CONSTRAINT ckc_estado_seguro_cliente CHECK (estado IN ('ACTIVO','INACTIVO')),
   version              NUMERIC(9) NOT NULL,
   CONSTRAINT pk_seguros_prestamo_cliente PRIMARY KEY (id_seguro_prestamo_cliente)
);

COMMENT ON TABLE seguros_prestamo_cliente IS
'Tabla de relacion entre seguros y prestamos especificos de clientes.';

COMMENT ON COLUMN seguros_prestamo_cliente.id_seguro_prestamo_cliente IS
'Identificador unico de la relacion seguro-prestamo-cliente.';

COMMENT ON COLUMN seguros_prestamo_cliente.id_prestamo_cliente IS
'Clave foranea que referencia el prestamo del cliente.';

COMMENT ON COLUMN seguros_prestamo_cliente.id_seguro_prestamo IS
'Clave foranea que referencia la relacion seguro-prestamo.';

COMMENT ON COLUMN seguros_prestamo_cliente.monto_total IS
'Monto total del seguro asociado al prestamo.';

COMMENT ON COLUMN seguros_prestamo_cliente.monto_cuota IS
'Monto del seguro incluido en cada cuota.';

COMMENT ON COLUMN seguros_prestamo_cliente.estado IS
'Estado de la relacion (ACTIVO, INACTIVO).';

COMMENT ON COLUMN seguros_prestamo_cliente.version IS
'Numero de version del registro para control de concurrencia.';

/*==============================================================*/
/* Relaciones                                                   */
/*==============================================================*/
ALTER TABLE cronogramas_pagos
   ADD CONSTRAINT fk_cronogramas_pagos_prestamos_clientes 
   FOREIGN KEY (id_prestamo_cliente) REFERENCES prestamos_clientes (id_prestamo_cliente)
   ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE esquemas_amortizacion
   ADD CONSTRAINT fk_esquemas_amortizacion_tipos_prestamos 
   FOREIGN KEY (id_tipo_prestamo) REFERENCES tipos_prestamos (id_tipo_prestamo)
   ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE garantias_tipos_prestamos
   ADD CONSTRAINT fk_garantias_tipos_prestamos_tipos_prestamos 
   FOREIGN KEY (id_tipo_prestamo) REFERENCES tipos_prestamos (id_tipo_prestamo)
   ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE garantias_tipos_prestamos
   ADD CONSTRAINT fk_garantias_tipos_prestamos_garantias 
   FOREIGN KEY (id_garantia) REFERENCES garantias (id_garantia)
   ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE garantias_tipos_prestamos_cliente
   ADD CONSTRAINT fk_garantias_tipos_prestamos_cliente_prestamos_clientes 
   FOREIGN KEY (id_prestamo_cliente) REFERENCES prestamos_clientes (id_prestamo_cliente)
   ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE garantias_tipos_prestamos_cliente
   ADD CONSTRAINT fk_garantias_tipos_prestamos_cliente_garantias_tipos 
   FOREIGN KEY (id_garantia_tipo_prestamo) REFERENCES garantias_tipos_prestamos (id_garantia_tipo_prestamo)
   ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE pagos_prestamos
   ADD CONSTRAINT fk_pagos_prestamos_cronogramas_pagos 
   FOREIGN KEY (id_cuota) REFERENCES cronogramas_pagos (id_cuota)
   ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE prestamos
   ADD CONSTRAINT fk_prestamos_tipos_prestamos 
   FOREIGN KEY (id_tipo_prestamo) REFERENCES tipos_prestamos (id_tipo_prestamo)
   ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE prestamos_clientes
   ADD CONSTRAINT fk_prestamos_clientes_prestamos 
   FOREIGN KEY (id_prestamo) REFERENCES prestamos (id_prestamo)
   ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE seguros_prestamo_cliente
   ADD CONSTRAINT fk_seguros_prestamo_cliente_seguros_prestamos 
   FOREIGN KEY (id_seguro_prestamo) REFERENCES seguros_prestamos (id_seguro_prestamo)
   ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE seguros_prestamo_cliente
   ADD CONSTRAINT fk_seguros_prestamo_cliente_prestamos_clientes 
   FOREIGN KEY (id_prestamo_cliente) REFERENCES prestamos_clientes (id_prestamo_cliente)
   ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE seguros_prestamos
   ADD CONSTRAINT fk_seguros_prestamos_prestamos 
   FOREIGN KEY (id_prestamo) REFERENCES prestamos (id_prestamo)
   ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE seguros_prestamos
   ADD CONSTRAINT fk_seguros_prestamos_seguros 
   FOREIGN KEY (id_seguro) REFERENCES seguros (id_seguro)
   ON DELETE RESTRICT ON UPDATE RESTRICT;

alter table prestamos
   add constraint fk_prestamos_monedas foreign key (id_moneda)
      references monedas (id_moneda)
      on delete restrict on update restrict;

alter table tipos_prestamos
   add constraint fk_tipos_prestamos_monedas foreign key (id_moneda)
      references monedas (id_moneda)
      on delete restrict on update restrict;

alter table prestamos_clientes
   add constraint fk_prestamos_clientes_clientes foreign key (id_cliente)
      references clientes (id_cliente)
      on delete restrict on update restrict;