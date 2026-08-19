package Controller;

import Model.Produto;
import Service.ListaProdutos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api")
public class Estoque {


    ListaProdutos listaProdutos = new ListaProdutos();
    List<Produto> produtos = new ArrayList<>();

    @GetMapping("/estoque-critico")
    public ResponseEntity<List<Produto>> listarEstoque(){

        produtos  = listaProdutos.ListarProdutos();

        return ResponseEntity.ok(produtos);

    }
}
