/* AssentSoftware (C)2021 */
package com.greek.service.manager;

public interface OrganizationService {

    public enum TipoDocumentoEnum {
        ACREEDOR(13L),
        CASO(2L),
        COMPRA(14L),
        CONTROL(4L),
        EMPLEADO(12L),
        FACTURA(5L),
        FORMA_LIBRE(9L),
        HISTORIA(1L),
        MISCELANEO(100L),
        NOTA_CREDITO(7L),
        NOTA_DEBITO(6L),
        ORDEN_ENTREGA(8L),
        ORDEN_SALIDA_EXPEDIENTE(11L),
        PEDIDO(15L),
        RECIBO_PAGO(10L),
        RECIBO_PAGO_TERCERO(3L);

        Long tipoDocumentoId;

        TipoDocumentoEnum(Long tipoDocumentoId) {
            this.tipoDocumentoId = tipoDocumentoId;
        }

        public Long getIdCategoriaPersona() {
            return tipoDocumentoId;
        }
    }

    Long getNextCodeNumber(Long organizacionId, TipoDocumentoEnum historia);
}
