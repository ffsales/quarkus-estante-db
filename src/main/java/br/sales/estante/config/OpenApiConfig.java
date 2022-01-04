package br.sales.estante.config;


import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.servers.Server;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.ws.rs.core.Application;

@OpenAPIDefinition(info = @Info(title = "Sistema de exemple de catálogo de livros",
        version = "1.0.0", contact = @Contact(name = "Felipe Sales",
        email = "felipe.ferreira.sales@gmail",
        url = "https://github.com/ffsales")),
        tags = {@Tag(name = "/v1/books", description = "Gerenciamento de livros persistindo no postgres")},
        servers = {@Server(url = "http://localhost:8082")})
public class OpenApiConfig extends Application {
}
