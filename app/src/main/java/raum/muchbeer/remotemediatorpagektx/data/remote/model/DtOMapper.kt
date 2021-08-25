package raum.muchbeer.remotemediatorpagektx.data.remote.model

interface DtOMapper<Response, Model> {

    abstract fun mapFromResponseToModel(response: Response) : Model
}