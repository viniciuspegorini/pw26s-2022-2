import React from 'react';
import BaseRoutes from './routes/BaseRoutes';
import "./App.css";
import { ChakraProvider } from '@chakra-ui/react';
import { AuthProvider } from './context/AuthContext';

function App() {
  return (
    <ChakraProvider>
      <AuthProvider>
        <BaseRoutes />
      </AuthProvider>
    </ChakraProvider>
  );
}

export default App;
