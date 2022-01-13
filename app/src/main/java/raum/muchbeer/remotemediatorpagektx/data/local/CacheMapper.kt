package raum.muchbeer.remotemediatorpagektx.data.local

interface CacheMapper<DtoModel, CacheModel> {

     fun mapFromDtoModelToCacheModel(model: List<DtoModel>) : List<CacheModel>
}