export default async (entity,id) => {
    try {
      const response = await fetch(`http://localhost:8080/api/${entity}?id=${id}`,{method: 'DELETE'})

      const success = (response.status == 200)

      return { success, response}

    } catch (error) {
        alert(`Error DELETE: ${error}!`)
        return null
    }
}