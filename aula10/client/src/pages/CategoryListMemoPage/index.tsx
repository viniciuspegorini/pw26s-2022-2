import { ChangeEvent, useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { Category } from "../../commons/types";
import { Table } from "../../components/Table";
import CategoryService from "../../services/CategoryService";

export function CategoryMemoPage() {
  const [data, setData] = useState([]);
  const [apiError, setApiError] = useState("");
  const [filter, setFilter] = useState("");
  const [isDark, setIsDark] = useState(false);

  useEffect(() => {
    loadData();
  }, []);

  const loadData = () => {
    CategoryService.findAll()
      .then((response) => {
        setData(response.data);
        setApiError("");
      })
      .catch((error) => {
        setApiError("Falha ao carregar a lista de categorias");
      });
  };

  const onChange = (event: ChangeEvent<HTMLInputElement>) => {
    setFilter(event.target.value);
  };

  const onRemove = (id: number) => {
    CategoryService.remove(id)
      .then((response) => {
        loadData();
        setApiError("");
      })
      .catch((erro) => {
        setApiError("Falha ao remover a categoria");
      });
  };

  return (
    <main className="container">
      <div className="bg-light p-5 rounded">
        <h6 className="border-bottom border-gray pb-2 mb-3">
          Lista de Categoria - useMemo
        </h6>
        <div className="text-center mb-3">
          <Link className="btn btn-success" to="/categories/new">
            Nova Categoria
          </Link>
        </div>
        <div className="form-floating">
          <input
            className="form-control"
            placeholder="Filtro"
            type="text"
            value={filter}
            onChange={onChange}
          />
          <label>Filtro</label>
        </div>
        <div className="form-check mb-3">
          <label className="form-check-label">
            <input
              className="form-check-input"
              type="checkbox"
              checked={isDark}
              onChange={(e) => setIsDark(e.target.checked)}
            />
            Dark mode
          </label>
        </div>
        <Table
          theme={
            isDark ? "table table-dark table-striped" : "table table-striped"
          }
          list={data}
          filter={filter}
        />
        {apiError && <div className="alert alert-danger">{apiError}</div>}
      </div>
    </main>
  );
}
