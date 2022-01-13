package raum.muchbeer.remotemediatorpagektx.data.remote.model

interface DtOMapper<Response, Model> {
     fun mapFromResponseToModel(response: Response) : Model
}