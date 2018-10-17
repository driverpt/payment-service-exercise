package net.luisduarte.revolut.exercise.services

interface IService<T, K> {
    fun listAll(): List<T>
    fun findById(uuid: K): T?
    fun create(dao: T): T
    fun update(dao: T): T
}