export default async (entity, body) => {
    try {
        const response = await fetch(`http://localhost:8080/api/${entity}`,{
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(body)
        })

        const success = (response.status == 200)

        return { success, response}

    } catch (error) {
        alert(`Error PUT: ${error}!`)
        return null
    }
}