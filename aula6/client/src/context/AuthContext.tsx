import { createContext, ReactNode } from 'react';
import { UserLogin } from '../commons/types';
import { useAuth } from './hooks/useAuth';

interface AuthContextType {
  authenticated: boolean;
  loading: boolean;
  handleLogin: (user: UserLogin) => void;
  handleLoginSocial: (idToken: string) => void;
  handleLogout: () => void;
}

interface AuthProviderProps {
  children: ReactNode
}

export const AuthContext = createContext({} as AuthContextType);

export function AuthProvider({ children }: AuthProviderProps) {
  const {authenticated, loading, handleLogin, handleLoginSocial, handleLogout} = useAuth();

  return (
    <AuthContext.Provider value={{loading, authenticated, handleLogin, handleLoginSocial, handleLogout}}>
      {children}
    </AuthContext.Provider>
  )
}