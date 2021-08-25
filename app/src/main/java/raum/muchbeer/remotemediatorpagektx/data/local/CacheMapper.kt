package raum.muchbeer.remotemediatorpagektx.data.local

interface CacheMapper<DtoModel, CacheModel> {

    abstract fun mapFromDtoModelToCacheModel(model: List<DtoModel>) : List<CacheModel>
}