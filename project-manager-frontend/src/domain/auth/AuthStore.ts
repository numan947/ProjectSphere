import { create } from "zustand";
import { createJSONStorage, persist } from "zustand/middleware";
import { mountStoreDevtool } from 'simple-zustand-devtools';

interface AuthStore{
    token: string;
    userFullName: string;
    userEmail: string;
    userId: string;
    setUser: (user: {fullName: string, email: string, id: string, token:string}) => void;
    logout(): void;
}

const useAuthStore = create<AuthStore>()(
    persist(
      (set) => ({
        token: '',
        userFullName: '',
        userEmail: '',
        userId: '',
        setUser: (user) => set((state) => ({...state, userFullName: user.fullName, userEmail: user.email, userId: user.id, token: user.token})),
        logout: () => set((state) => ({...state, token: '', userFullName: '', userEmail: '', userId: ''}))
      }),
      {
        name: 'jwt-token-store', // name of the item in the storage (must be unique)
        storage: createJSONStorage(() => sessionStorage), // (optional) by default, 'localStorage' is used
      },
    ),
  )


if (process.env.NODE_ENV === 'development')
    mountStoreDevtool('Counter Store', useAuthStore);
  

export default useAuthStore;