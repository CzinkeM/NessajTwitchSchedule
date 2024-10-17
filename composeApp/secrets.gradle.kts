import java.util.Properties

fun loadSecrets(path: String): Properties {
    val file = rootDir.resolve(path)
    val properties = Properties()

    if (file.exists()) {
        properties.load(file.inputStream())
    } else {
        throw GradleException("Secrets file not found at $file")
    }

    return properties
}

val secretsProperties = loadSecrets("secrets.properties")
extra["secretsProperties"] = secretsProperties