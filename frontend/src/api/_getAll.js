export default async (entity) => {
    try {
        const response = await fetch(`http://localhost:8080/api/${entity}`)

        if(response.status == 200) {
            return { success : true, list: await response.json()}
        }

        return { success : false, response}
    } catch (error) {
        alert(`Error GET: ${error}!`)
        return null
    }
}