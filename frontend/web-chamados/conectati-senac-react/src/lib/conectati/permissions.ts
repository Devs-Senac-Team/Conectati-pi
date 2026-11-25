import type { TipoUsuario } from "@/services/conectati";
import { getUsuario } from "@/services/session";

export type PerfilKey = TipoUsuario;
export type MenuKey =
  | "home"
  | "dashboard"
  | "chamados"
  | "novo-chamado"
  | "equipamentos"
  | "relatorios"
  | "minhas-turmas"
  | "mapa-salas"
  | "usuarios"
  | "unidades";
export type Perm = {
  menu: MenuKey[];
  verChamados: "todos" | "proprios";
  acoes: boolean;
  novoChamado: boolean;
  cor: string;
  descricao: string;
};

export const PERMISSOES: Partial<Record<PerfilKey, Perm>> = {
  INSTRUTOR: {
    menu: ["home", "chamados", "novo-chamado", "minhas-turmas"],
    verChamados: "proprios",
    acoes: false,
    novoChamado: true,
    cor: "#2b8cff",
    descricao: "Solicita e acompanha os proprios chamados.",
  },
  COORDENADOR: {
    menu: ["home", "dashboard", "chamados", "novo-chamado", "equipamentos", "mapa-salas"],
    verChamados: "proprios",
    acoes: false,
    novoChamado: true,
    cor: "#7c3aed",
    descricao: "Acompanha solicitacoes e equipamentos.",
  },
};

export function getPerfil(): PerfilKey | "" {
  return getUsuario()?.tipo || "";
}
export function getPermissoes(): Perm | null {
  const perfil = getPerfil();
  return perfil ? (PERMISSOES[perfil] ?? null) : null;
}
export function paginaInicialPerfil(perm: Perm): MenuKey {
  return perm.menu.find((item) => item !== "home") || "dashboard";
}
