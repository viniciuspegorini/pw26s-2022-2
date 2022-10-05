import { api } from '../lib/axios';
import { Product } from '../commons/types';

const save = (product: Product) => {
    return api.post('/products', product);
}

const saveAndUpload = (formData: FormData) => {
    return api.post('/products', formData);
}

const findAll = () => {
    return api.get('/products');
}

const findOne = (id: number) => {
    return api.get(`/products/${id}`);
}

const remove = (id: number) => {
    return api.delete(`/products/${id}`);
}

const ProductService = {
    save,
    saveAndUpload,
    findAll,
    findOne,
    remove
}

export default ProductService;