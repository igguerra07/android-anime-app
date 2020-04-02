package br.com.igguerra.animeapp.network

data class Outcome<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): Outcome<T> {
            return Outcome(
                Status.SUCCESS,
                data,
                null
            )
        }

        fun <T> error(msg: String): Outcome<T> {
            return Outcome(
                Status.ERROR,
                null,
                msg
            )
        }

        fun <T> loading(): Outcome<T> {
            return Outcome(
                Status.LOADING,
                null,
                null
            )
        }
    }
}