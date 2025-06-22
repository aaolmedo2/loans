# LOANS MODULE 💵

## MODEL
![Descripción de la imagen](src\main\resources\static\loans-schema.jpg)

## TABLES(in order)
- EsquemasAmortizacion
- Garantia
- Seguro
- TiposComisione
---
- TiposPrestamo
- Prestamo
- PrestamosCliente
---
- GarantiasTiposPrestamo
- ComisionesPrestamo
- SegurosPrestamo
---
- GarantiasTiposPrestamosCliente
- SegurosPrestamoCliente
- ComisionesPrestamoCliente
---
- CronogramasPago
- PagosPrestamo

## RELATIONS
- prestamos -> monedas
```bash
    alter table prestamos
    add constraint fk_prestamos_monedas foreign key (id_moneda)
        references monedas (id_moneda)
        on delete restrict on update restrict;
```
- tipos_prestamos -> monedas
```bash
    alter table tipos_prestamos
    add constraint fk_tipos_prestamos_monedas foreign key (id_moneda)
        references monedas (id_moneda)
        on delete restrict on update restrict;
```
- prestamos_clientes -> clientes
```bash
    alter table prestamos_clientes
    add constraint fk_prestamos_clientes_clientes foreign key (id_cliente)
        references clientes (id_cliente)
        on delete restrict on update restrict;
```

## CONSTRAINT OF LOANS

| TABLE                         | ATTRIBUTE        | CONSTRAINT                                                                                       | ENUM                        |
|:------------------------------|:------------------|:--------------------------------------------------------------------------------------------------|:-----------------------------|
| comisiones_prestamo_cliente   | `estado`          | ▫️ 'PENDIENTE'<br>▫️ 'CANCELADA'<br>▫️ 'EXENTA'                                                   | EstadoComisionClienteEnum   |
| comisiones_prestamos          | `estado`          | ▫️ 'ACTIVO'<br>▫️ 'INACTIVO'                                                                      | EstadoGeneralEnum           |
| tipos_comisiones              | `tipo`            | ▫️ 'ORIGINACION'<br>▫️ 'PAGO ATRASADO'<br>▫️ 'PREPAGO'<br>▫️ 'MODIFICACION'<br>▫️ 'SERVICIO ADICIONAL' | TipoComisionEnum            |
| tipos_comisiones              | `tipo_calculo`    | ▫️ 'PORCENTAJE'<br>▫️ 'FIJO'                                                                      | TipoCalculoComisionEnum     |
| tipos_comisiones              | `estado`          | ▫️ 'ACTIVO'<br>▫️ 'INACTIVO'                                                                      | EstadoGeneralEnum           |
| cronogramas_pagos             | `estado`          | ▫️ 'ACTIVO'<br>▫️ 'INACTIVO'<br>▫️ 'PENDIENTE'<br>▫️ 'PAGADO'                                     | EstadoCronogramaEnum        |
| esquemas_amortizacion         | `nombre`          | ▫️ 'FRANCES'<br>▫️ 'AMERICANO'<br>▫️ 'ALEMAN'                                                     | EsquemaAmortizacionEnum     |
| esquemas_amortizacion         | `estado`          | ▫️ 'ACTIVO'<br>▫️ 'INACTIVO'                                                                      | EstadoGeneralEnum           |
| garantias                     | `tipo_garantia`   | ▫️ 'HIPOTECA'<br>▫️ 'PRENDARIA'<br>▫️ 'PERSONAL'                                                  | TipoGarantiaEnum            |
| garantias                     | `estado`          | ▫️ 'ACTIVO'<br>▫️ 'INACTIVO'                                                                      | EstadoGeneralEnum           |
| garantias_tipos_prestamos     | `estado`          | ▫️ 'ACTIVO'<br>▫️ 'INACTIVO'                                                                      | EstadoGeneralEnum           |
| garantias_tipos_prestamos_cliente | `estado`     | ▫️ 'ACTIVO'<br>▫️ 'INACTIVO'                                                                      | EstadoGeneralEnum           |
| pagos_prestamos               | `estado`          | ▫️ 'COMPLETADO'<br>▫️ 'REVERTIDO'                                                                 | EstadoPagoEnum              |
| tipos_prestamos               | `tipo_cliente`    | ▫️ 'PERSONA'<br>▫️ 'EMPRESA'<br>▫️ 'AMBOS'                                                        | TipoClienteEnum             |
| tipos_prestamos               | `estado`          | ▫️ 'ACTIVO'<br>▫️ 'INACTIVO'                                                                      | EstadoGeneralEnum           |
| prestamos                     | `base_calculo`    | ▫️ '30/360'<br>▫️ '31/365'                                                                        | BaseCalculoEnum             |
| prestamos                     | `estado`          | ▫️ 'ACTIVO'<br>▫️ 'INACTIVO'<br>▫️ 'SOLICITADO'                                                   | EstadoPrestamoEnum          |
| prestamos_clientes            | `estado`          | ▫️ 'SOLICITADO'<br>▫️ 'APROBADO'<br>▫️ 'DESEMBOLSADO'<br>▫️ 'VIGENTE'<br>▫️ 'EN_MORA'<br>▫️ 'REFINANCIADO'<br>▫️ 'PAGADO'<br>▫️ 'CASTIGADO' | EstadoPrestamoClienteEnum |
| seguros                       | `tipo_seguro`     | ▫️ 'VIDA'<br>▫️ 'DESEMPLEO'<br>▫️ 'PROTECCION_PAGOS'<br>▫️ 'DESGRAVAMEN'<br>▫️ 'INCENDIOS'         | TipoSeguroEnum              |
| seguros                       | `estado`          | ▫️ 'ACTIVO'<br>▫️ 'VENCIDO'<br>▫️ 'CANCELADO'<br>▫️ 'INACTIVO'                                    | EstadoSeguroEnum            |
| seguros_prestamos             | `estado`          | ▫️ 'ACTIVO'<br>▫️ 'INACTIVO'                                                                      | EstadoGeneralEnum           |
| seguros_prestamo_cliente      | `estado`          | ▫️ 'ACTIVO'<br>▫️ 'INACTIVO'                                                                      | EstadoGeneralEnum           |

## ENDPOINTS

