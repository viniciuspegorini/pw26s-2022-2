import React from 'react';
import BaseRoutes from './routes/BaseRoutes';
import "./App.css";
import { ChakraProvider } from '@chakra-ui/react';
import {AuthProvider} from './context/AuthContext';
import axios from 'axios';

function App() {
  axios.defaults.baseURL = "http://localhost:8080";
  return (
    <ChakraProvider>
      <AuthProvider>
        <BaseRoutes />
      </AuthProvider>
    </ChakraProvider>
  );
}

export default App;
